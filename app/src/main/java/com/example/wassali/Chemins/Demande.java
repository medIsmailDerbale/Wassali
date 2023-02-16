package com.example.wassali.Chemins;

public class Demande {

    public String DemandeID , poids , taille , desc , userID , cheminID , etat;

    public Demande() {
    }

    public Demande(String DemandeID ,String poids, String taille, String desc, String userID, String cheminID) {
        this.DemandeID = DemandeID ;
        this.poids = poids;
        this.taille = taille;
        this.desc = desc;
        this.userID = userID;
        this.cheminID = cheminID;
        this.etat = "en attente" ;
    }
}
