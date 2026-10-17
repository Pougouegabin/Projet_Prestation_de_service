package com.example.backend.model;

import jakarta.persistence.*;

@Entity @Table(name="zone_intervention", uniqueConstraints=@UniqueConstraint(name="uk_zone_prestataire_localite", columnNames={"prestataire_id","localite_id"}))
public class ZoneIntervention {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="prestataire_id", nullable=false) private Prestataire prestataire;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="localite_id", nullable=false) private Localite localite;
    public Long getId(){return id;} public Prestataire getPrestataire(){return prestataire;} public void setPrestataire(Prestataire v){prestataire=v;} public Localite getLocalite(){return localite;} public void setLocalite(Localite v){localite=v;}
}
