package com.example.wassali.Chemins;

public class DemandeModel {
    public String demandeID ,poids , taille , desc , etat;

    public DemandeModel() {
    }

    public DemandeModel(String poids, String taille , String desc , String etat , String demandeID) {
        this.poids = poids;
        this.taille = taille;
        this.desc = desc;
        this.etat = etat;
        this.demandeID = demandeID ;
    }
}
