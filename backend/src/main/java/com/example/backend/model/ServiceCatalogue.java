package com.example.backend.model;

import jakarta.persistence.*;

@Entity @Table(name="service")
public class ServiceCatalogue {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="categorie_id", nullable=false) private Categorie categorie;
    @Column(nullable=false) private String nom;
    @Column(length=2000) private String description;
    public Long getId(){return id;} public Categorie getCategorie(){return categorie;} public void setCategorie(Categorie v){categorie=v;} public String getNom(){return nom;} public void setNom(String v){nom=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
