package com.example.backend.Repository;
import com.example.backend.model.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PrestationRepository extends JpaRepository<Prestation, Long> {
    boolean existsByPrestataireIdAndServiceId(Long prestataireId, Long serviceId);
    boolean existsByPrestataireIdAndServiceIdAndStatut(Long prestataireId, Long serviceId, com.example.backend.model.StatutPrestation statut);
    java.util.List<Prestation> findByServiceIdAndStatut(Long serviceId, com.example.backend.model.StatutPrestation statut);
}
