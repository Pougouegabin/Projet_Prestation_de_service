package com.example.backend.controller;

import com.example.backend.Repository.*;
import com.example.backend.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@RestController @RequestMapping("/api/prestataires")
public class PrestataireController {
    private final UsersRepostory users; private final PrestataireRepository prestataires;
    private final ServiceCatalogueRepository services; private final PrestationRepository prestations;
    private final LocaliteRepository localites; private final ZoneInterventionRepository zones;
    private final AvisRepository avis;
    public PrestataireController(UsersRepostory users, PrestataireRepository prestataires, ServiceCatalogueRepository services,
            PrestationRepository prestations, LocaliteRepository localites, ZoneInterventionRepository zones, AvisRepository avis) {
        this.users=users; this.prestataires=prestataires; this.services=services; this.prestations=prestations; this.localites=localites; this.zones=zones; this.avis=avis;
    }
    public record Offre(Long serviceId, String description, BigDecimal tarifIndicatif) {}
    public record ProfilRequest(@NotBlank String metier, String presentation, @NotEmpty List<Offre> offres, @NotEmpty List<Long> localiteIds) {}
    public record AvisResume(int note, String commentaire) {}
    public record ProfilResultat(Long id, String nom, String prenom, String metier, String presentation, String telephone,
                                 double noteMoyenne, long nombreAvis, List<AvisResume> avis) {}

    @PostMapping("/profil") @ResponseStatus(HttpStatus.CREATED) @Transactional
    public Map<String,Object> creerProfil(@Valid @RequestBody ProfilRequest dto, Authentication auth) {
        Users compte=users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
        if(prestataires.findByUtilisateurId(compte.getId()).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Ce compte possède déjà un profil prestataire.");
        Prestataire p=new Prestataire(); p.setUtilisateur(compte); p.setMetier(dto.metier()); p.setPresentation(dto.presentation()); p=prestataires.save(p);
        Set<Long> serviceIds=new HashSet<>();
        for(Offre offre:dto.offres()) {
            if(!serviceIds.add(offre.serviceId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Un service ne peut être ajouté qu'une fois.");
            ServiceCatalogue s=services.findById(offre.serviceId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service introuvable."));
            Prestation pr=new Prestation(); pr.setPrestataire(p); pr.setService(s); pr.setDescription(offre.description()); pr.setTarifIndicatif(offre.tarifIndicatif()); prestations.save(pr);
        }
        Set<Long> localiteIds=new HashSet<>();
        for(Long id:dto.localiteIds()) {
            if(!localiteIds.add(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Une localité ne peut être ajoutée qu'une fois.");
            Localite l=localites.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Localité introuvable."));
            ZoneIntervention z=new ZoneIntervention(); z.setPrestataire(p); z.setLocalite(l); zones.save(z);
        }
        return Map.of("id",p.getId(),"statut",p.getStatutValidation(),"message","Profil soumis à validation.");
    }

    @GetMapping("/recherche/{serviceId}/{localiteId}") @Transactional(readOnly=true)
    public List<ProfilResultat> rechercher(@PathVariable Long serviceId, @PathVariable Long localiteId) {
        return prestations.findByServiceIdAndStatut(serviceId, StatutPrestation.ACTIVE).stream()
                .map(Prestation::getPrestataire).distinct()
                .filter(p -> p.getStatutValidation()==StatutPrestataire.VALIDE && zones.existsByPrestataireIdAndLocaliteId(p.getId(),localiteId))
                .map(p -> {
                    Users u=p.getUtilisateur(); List<Avis> liste=avis.findByDemandePrestataireId(p.getId());
                    double moyenne=liste.isEmpty()?0.0:liste.stream().mapToInt(Avis::getNote).average().orElse(0.0);
                    return new ProfilResultat(p.getId(),u.getName(),u.getPrenom(),p.getMetier(),p.getPresentation(),u.getTelephone(),moyenne,liste.size(),
                            liste.stream().map(a->new AvisResume(a.getNote(),a.getCommentaire())).toList());
                }).toList();
    }
}
