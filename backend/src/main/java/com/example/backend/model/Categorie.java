package com.example.backend.model;

import jakarta.persistence.*;

@Entity @Table(name="categorie")
public class Categorie {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String nom;
    @Column(length=1000) private String description;
    public Long getId(){return id;} public String getNom(){return nom;} public void setNom(String v){nom=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
