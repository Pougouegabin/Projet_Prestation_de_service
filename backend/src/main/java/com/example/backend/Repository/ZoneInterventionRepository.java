package com.example.backend.Repository;
import com.example.backend.model.ZoneIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ZoneInterventionRepository extends JpaRepository<ZoneIntervention, Long> {
    boolean existsByPrestataireIdAndLocaliteId(Long prestataireId, Long localiteId);
}
