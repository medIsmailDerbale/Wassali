package com.example.wassali.Chemins;

public class DemandeModel {
    public String DemandeID ,poids , taille , desc , etat;

    public DemandeModel() {
    }

    public DemandeModel(String poids, String taille , String desc , String etat , String DemandeID) {
        this.poids = poids;
        this.taille = taille;
        this.desc = desc;
        this.etat = etat;
        this.DemandeID = DemandeID ;
    }
}
