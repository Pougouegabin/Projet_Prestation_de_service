package com.example.backend.controller;

import com.example.backend.Repository.*;
import com.example.backend.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController @RequestMapping("/api/demandes")
public class DemandeController {
    private final UsersRepostory users; private final DemandeRepository demandes; private final ServiceCatalogueRepository services;
    private final LocaliteRepository localites; private final PrestataireRepository prestataires;
    private final PrestationRepository prestations; private final ZoneInterventionRepository zones;
    public DemandeController(UsersRepostory users, DemandeRepository demandes, ServiceCatalogueRepository services,
            LocaliteRepository localites, PrestataireRepository prestataires, PrestationRepository prestations, ZoneInterventionRepository zones) {
        this.users=users; this.demandes=demandes; this.services=services; this.localites=localites; this.prestataires=prestataires; this.prestations=prestations; this.zones=zones;
    }
    public record DemandeRequest(@NotNull Long serviceId, @NotNull Long localiteId, @NotNull Long prestataireId,
                                 @NotBlank String description, LocalDateTime dateSouhaitee) {}

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional
    public Map<String,Object> creer(@Valid @RequestBody DemandeRequest dto, Authentication auth) {
        Users client=users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
        ServiceCatalogue service=services.findById(dto.serviceId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service introuvable."));
        Localite localite=localites.findById(dto.localiteId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Localité introuvable."));
        Prestataire pro=prestataires.findById(dto.prestataireId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Prestataire introuvable."));
        if(pro.getStatutValidation()!=StatutPrestataire.VALIDE) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ce profil prestataire n'est pas validé.");
        if(!prestations.existsByPrestataireIdAndServiceIdAndStatut(pro.getId(),service.getId(),StatutPrestation.ACTIVE)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ce prestataire ne propose pas ce service.");
        if(!zones.existsByPrestataireIdAndLocaliteId(pro.getId(),localite.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ce prestataire n'intervient pas dans cette localité.");
        Demande d=new Demande(); d.setUtilisateur(client); d.setService(service); d.setLocalite(localite); d.setPrestataire(pro);
        d.setDescription(dto.description()); d.setDateSouhaitee(dto.dateSouhaitee()); d=demandes.save(d);
        String phone=pro.getUtilisateur().getTelephone()==null?"":pro.getUtilisateur().getTelephone().replaceAll("\\D","");
        String message=URLEncoder.encode("Bonjour, je vous contacte via ProxiService pour : "+dto.description(),StandardCharsets.UTF_8);
        return Map.of("demandeId",d.getId(),"statut",d.getStatut(),"whatsAppUrl","https://wa.me/"+phone+"?text="+message);
    }
}
