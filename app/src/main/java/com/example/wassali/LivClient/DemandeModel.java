package com.example.wassali.LivClient;

public class DemandeModel {

    public String desc , etat , demandeID;

    public DemandeModel(String desc, String etat,String demandeID) {
        this.desc = desc;
        this.etat = etat;
        this.demandeID = demandeID;
    }


    public DemandeModel() {

    }

    public String getDesc() {
        return desc;
    }

    public String getDemandeID() {
        return demandeID;
    }

    public String getEtat() {
        return etat;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDemandeID(String demandeID) {
        this.demandeID = demandeID;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
