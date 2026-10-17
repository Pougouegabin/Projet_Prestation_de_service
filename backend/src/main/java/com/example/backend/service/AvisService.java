package com.example.backend.service;

import com.example.backend.Repository.AvisRepository;
import com.example.backend.Repository.DemandeRepository;
import com.example.backend.model.Avis;
import com.example.backend.model.Demande;
import com.example.backend.ecseption.RegleMetierException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AvisService {
    private final AvisRepository avisRepository;
    private final DemandeRepository demandeRepository;
    public AvisService(AvisRepository avisRepository, DemandeRepository demandeRepository) {
        this.avisRepository = avisRepository; this.demandeRepository = demandeRepository;
    }
    @Transactional
    public Avis publier(Long demandeId, int note, String commentaire) {
        if (note < 1 || note > 5) throw new RegleMetierException("La note doit être comprise entre 1 et 5.");
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RegleMetierException("Demande introuvable : " + demandeId));
        if (!demande.isRealisationConfirmee()) throw new RegleMetierException("La réalisation doit être confirmée par les deux parties avant de publier un avis.");
        if (avisRepository.existsByDemandeId(demandeId)) throw new RegleMetierException("Un avis existe déjà pour cette demande.");
        Avis avis = new Avis(); avis.setDemande(demande); avis.setNote(note); avis.setCommentaire(commentaire);
        return avisRepository.save(avis);
    }
}
