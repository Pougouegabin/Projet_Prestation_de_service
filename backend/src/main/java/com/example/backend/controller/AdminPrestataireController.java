package com.example.backend.controller;

import com.example.backend.Repository.PrestataireRepository;
import com.example.backend.model.Prestataire;
import com.example.backend.model.StatutPrestataire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.MessageDigest;
import java.util.Map;

@RestController @RequestMapping("/api/admin/prestataires")
public class AdminPrestataireController {
    private final PrestataireRepository prestataires;
    @Value("${proxiservice.admin.key:}") private String adminKey;
    public AdminPrestataireController(PrestataireRepository prestataires) { this.prestataires=prestataires; }

    @PatchMapping("/{id}/validation")
    public Map<String,Object> valider(@PathVariable Long id, @RequestHeader(name="X-Admin-Key", required=false) String key,
                                     @RequestParam boolean approuve) {
        if(adminKey.isBlank()) throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"La clé d'administration n'est pas configurée.");
        if(key==null || !MessageDigest.isEqual(adminKey.getBytes(java.nio.charset.StandardCharsets.UTF_8),key.getBytes(java.nio.charset.StandardCharsets.UTF_8)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Clé d'administration invalide.");
        Prestataire p=prestataires.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Profil prestataire introuvable."));
        p.setStatutValidation(approuve?StatutPrestataire.VALIDE:StatutPrestataire.REFUSE);
        prestataires.save(p);
        return Map.of("id",p.getId(),"statut",p.getStatutValidation());
    }
}
