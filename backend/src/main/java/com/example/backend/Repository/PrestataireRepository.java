package com.example.backend.Repository;
import com.example.backend.model.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {
    java.util.Optional<Prestataire> findByUtilisateurId(Long utilisateurId);
    java.util.List<Prestataire> findByStatutValidation(com.example.backend.model.StatutPrestataire statut);
}
