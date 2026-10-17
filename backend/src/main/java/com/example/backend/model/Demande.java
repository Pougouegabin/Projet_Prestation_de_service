package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="demande")
public class Demande {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="utilisateur_id", nullable=false) private Users utilisateur;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="service_id", nullable=false) private ServiceCatalogue service;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="localite_id", nullable=false) private Localite localite;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="prestataire_id") private Prestataire prestataire;
    @Column(length=2000, nullable=false) private String description;
    @Column(name="date_souhaitee") private LocalDateTime dateSouhaitee;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private StatutDemande statut=StatutDemande.EN_ATTENTE;
    @Column(name="confirmation_client", nullable=false) private boolean confirmationClient;
    @Column(name="confirmation_prestataire", nullable=false) private boolean confirmationPrestataire;
    @Column(name="date_confirmation_realisation") private LocalDateTime dateConfirmationRealisation;
    @Column(name="date_creation", nullable=false, updatable=false) private LocalDateTime dateCreation;
    @PrePersist void creation(){if(dateCreation==null)dateCreation=LocalDateTime.now();}
    public boolean isRealisationConfirmee(){return confirmationClient && confirmationPrestataire && dateConfirmationRealisation!=null;}
    public void confirmerParClient(){confirmationClient=true; actualiserConfirmation();}
    public void confirmerParPrestataire(){confirmationPrestataire=true; actualiserConfirmation();}
    private void actualiserConfirmation(){if(confirmationClient&&confirmationPrestataire){dateConfirmationRealisation=LocalDateTime.now();statut=StatutDemande.TERMINEE;}}
    public Long getId(){return id;} public Users getUtilisateur(){return utilisateur;} public void setUtilisateur(Users v){utilisateur=v;} public ServiceCatalogue getService(){return service;} public void setService(ServiceCatalogue v){service=v;}
    public Localite getLocalite(){return localite;} public void setLocalite(Localite v){localite=v;} public Prestataire getPrestataire(){return prestataire;} public void setPrestataire(Prestataire v){prestataire=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;} public LocalDateTime getDateSouhaitee(){return dateSouhaitee;} public void setDateSouhaitee(LocalDateTime v){dateSouhaitee=v;}
    public StatutDemande getStatut(){return statut;} public void setStatut(StatutDemande v){statut=v;} public boolean isConfirmationClient(){return confirmationClient;} public boolean isConfirmationPrestataire(){return confirmationPrestataire;}
    public LocalDateTime getDateConfirmationRealisation(){return dateConfirmationRealisation;} public LocalDateTime getDateCreation(){return dateCreation;}
}
