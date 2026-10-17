package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="avis")
public class Avis {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="demande_id", nullable=false, unique=true) private Demande demande;
    @Column(nullable=false) private int note;
    @Column(length=2000) private String commentaire;
    @Column(name="date_creation", nullable=false, updatable=false) private LocalDateTime dateCreation;
    @PrePersist void creation(){if(dateCreation==null)dateCreation=LocalDateTime.now();}
    public Long getId(){return id;} public Demande getDemande(){return demande;} public void setDemande(Demande v){demande=v;} public int getNote(){return note;} public void setNote(int v){note=v;} public String getCommentaire(){return commentaire;} public void setCommentaire(String v){commentaire=v;} public LocalDateTime getDateCreation(){return dateCreation;}
}
