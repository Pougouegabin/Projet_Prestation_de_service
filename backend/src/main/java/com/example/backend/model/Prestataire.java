package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="prestataire")
public class Prestataire {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="utilisateur_id", nullable=false, unique=true) private Users utilisateur;
    @Column(nullable=false) private String metier;
    @Column(length=2000) private String presentation;
    @Enumerated(EnumType.STRING) @Column(name="statut_validation", nullable=false) private StatutPrestataire statutValidation=StatutPrestataire.EN_ATTENTE;
    @Column(name="date_creation", nullable=false, updatable=false) private LocalDateTime dateCreation;
    @PrePersist void creation(){ if(dateCreation==null) dateCreation=LocalDateTime.now(); }
    public Long getId(){return id;} public Users getUtilisateur(){return utilisateur;} public void setUtilisateur(Users v){utilisateur=v;}
    public String getMetier(){return metier;} public void setMetier(String v){metier=v;} public String getPresentation(){return presentation;} public void setPresentation(String v){presentation=v;}
    public StatutPrestataire getStatutValidation(){return statutValidation;} public void setStatutValidation(StatutPrestataire v){statutValidation=v;} public LocalDateTime getDateCreation(){return dateCreation;}
}
