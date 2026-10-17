package com.example.backend.Repository;
import com.example.backend.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategorieRepository extends JpaRepository<Categorie, Long> {}
