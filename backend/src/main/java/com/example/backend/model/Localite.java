package com.example.backend.model;

import jakarta.persistence.*;

@Entity @Table(name="localite")
public class Localite {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String ville;
    @Column(nullable=false) private String quartier;
    public Long getId(){return id;} public String getVille(){return ville;} public void setVille(String v){ville=v;} public String getQuartier(){return quartier;} public void setQuartier(String v){quartier=v;}
}
