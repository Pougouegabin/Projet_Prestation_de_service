package com.example.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name="prestation", uniqueConstraints=@UniqueConstraint(name="uk_prestation_prestataire_service", columnNames={"prestataire_id","service_id"}))
public class Prestation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="prestataire_id", nullable=false) private Prestataire prestataire;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="service_id", nullable=false) private ServiceCatalogue service;
    @Column(length=2000) private String description;
    @Column(name="tarif_indicatif", precision=10, scale=2) private BigDecimal tarifIndicatif;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private StatutPrestation statut=StatutPrestation.ACTIVE;
    public Long getId(){return id;} public Prestataire getPrestataire(){return prestataire;} public void setPrestataire(Prestataire v){prestataire=v;} public ServiceCatalogue getService(){return service;} public void setService(ServiceCatalogue v){service=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;} public BigDecimal getTarifIndicatif(){return tarifIndicatif;} public void setTarifIndicatif(BigDecimal v){tarifIndicatif=v;} public StatutPrestation getStatut(){return statut;} public void setStatut(StatutPrestation v){statut=v;}
}
