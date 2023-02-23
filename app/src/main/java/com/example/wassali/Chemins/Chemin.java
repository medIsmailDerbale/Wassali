package com.example.wassali.Chemins;

public class Chemin {
    public String userID,adrDep , adrArr , dateDep , dateArr , heureDep , heureArr , cheminID ;
    public Double latitudeDep , longitudeDep , latitudeArr , longitudeArr ;

    public Chemin() {
    }
    // do enum
    public Chemin(String userID ,String cheminID,String adrDep,Double latitudeDep , Double longitudeDep, String adrArr,Double latitudeArr , Double longitudeArr, String dateDep, String dateArr, String heureDep, String heureArr) {
        this.userID =userID;
        this.cheminID = cheminID;
        this.adrDep = adrDep;
        this.latitudeDep = latitudeDep;
        this.longitudeDep = longitudeDep;
        this.adrArr = adrArr;
        this.latitudeArr = latitudeArr;
        this.longitudeArr = longitudeArr;
        this.dateDep = dateDep;
        this.dateArr = dateArr;
        this.heureDep = heureDep;
        this.heureArr = heureArr;
    }

    public String getUserID() {
        return userID;
    }

    public String getAdrDep() {
        return adrDep;
    }

    public String getAdrArr() {
        return adrArr;
    }

    public String getDateDep() {
        return dateDep;
    }

    public String getDateArr() {
        return dateArr;
    }

    public String getHeureDep() {
        return heureDep;
    }

    public String getHeureArr() {
        return heureArr;
    }

    public String getCheminID() {
        return cheminID;
    }

    public Double getLatitudeDep() {
        return latitudeDep;
    }

    public Double getLongitudeDep() {
        return longitudeDep;
    }

    public Double getLatitudeArr() {
        return latitudeArr;
    }

    public Double getLongitudeArr() {
        return longitudeArr;
    }
}
