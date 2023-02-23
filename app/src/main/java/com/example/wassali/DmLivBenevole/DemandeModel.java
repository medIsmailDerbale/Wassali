package com.example.wassali.DmLivBenevole;

public class DemandeModel {

    public String nomprenom , desc,demandeID, cheminID , userID , etat;

    public DemandeModel(String nomprenom, String desc,String demandeID,String cheminID ,String userID ,String etat) {
        this.nomprenom = nomprenom;
        this.desc = desc;
        this.demandeID = demandeID;
        this.cheminID=cheminID;
        this.userID=userID;
        this.etat=etat;
    }

    public DemandeModel() {
    }

    public String getEtat() {
        return etat;
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public String getDesc() {
        return desc;
    }

    public String getDemandeID() {
        return demandeID;
    }

    public String getCheminID() {
        return cheminID;
    }

    public String getUserID() {
        return userID;
    }

    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public void setCheminID(String cheminID) {
        this.cheminID = cheminID;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
