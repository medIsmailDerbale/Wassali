package com.example.wassali.Chemins;

public class Demande {

    public String poids , taille , desc , userID , cheminID;

    public Demande() {
    }

    public Demande(String poids, String taille, String desc, String userID, String cheminID) {
        this.poids = poids;
        this.taille = taille;
        this.desc = desc;
        this.userID = userID;
        this.cheminID = cheminID;
    }
}
