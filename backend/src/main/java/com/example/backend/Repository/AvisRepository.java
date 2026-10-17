package com.example.backend.Repository;
import com.example.backend.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AvisRepository extends JpaRepository<Avis, Long> {
    boolean existsByDemandeId(Long demandeId);
    java.util.List<Avis> findByDemandePrestataireId(Long prestataireId);
}
