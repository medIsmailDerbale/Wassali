package com.example.wassali.Chemins;

public class Livraison {

    public String livraisonID , demandeID , etat , cheminID , userID;

    public Livraison() {
    }


    public Livraison(String livraisonID, String demandeID, String cheminID, String userID) {
        this.livraisonID = livraisonID;
        this.demandeID = demandeID;
        this.cheminID = cheminID;
        this.userID = userID;
        this.etat = "en attente";
    }

    public String getLivraisonID() {
        return livraisonID;
    }

    public String getDemandeID() {
        return demandeID;
    }

    public String getEtat() {
        return etat;
    }

    public String getCheminID() {
        return cheminID;
    }

    public String getUserID() {
        return userID;
    }

    public void setLivraisonID(String livraisonID) {
        this.livraisonID = livraisonID;
    }

    public void setDemandeID(String demandeID) {
        this.demandeID = demandeID;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setCheminID(String cheminID) {
        this.cheminID = cheminID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
