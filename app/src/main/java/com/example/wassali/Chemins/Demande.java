package com.example.wassali.Chemins;

public class Demande {

    public String demandeID , poids , taille , desc , userID , cheminID , etat;

    public Demande() {
    }

    public Demande(String demandeID ,String poids, String taille, String desc, String userID, String cheminID) {
        this.demandeID = demandeID ;
        this.poids = poids;
        this.taille = taille;
        this.desc = desc;
        this.userID = userID;
        this.cheminID = cheminID;
        this.etat = "en attente" ;
    }


    public String getDemandeID() {
        return demandeID;
    }

    public String getPoids() {
        return poids;
    }

    public String getTaille() {
        return taille;
    }

    public String getDesc() {
        return desc;
    }

    public String getUserID() {
        return userID;
    }

    public String getCheminID() {
        return cheminID;
    }

    public String getEtat() {
        return etat;
    }
}
