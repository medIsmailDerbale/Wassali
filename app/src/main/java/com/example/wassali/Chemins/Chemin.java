package com.example.wassali.Chemins;

public class Chemin {
    public String adrDep , adrArr , dateDep , dateArr , heureDep , heureArr ;
    public Double latitudeDep , longitudeDep , latitudeArr , longitudeArr ;

    public Chemin() {
    }
    // do enum
    public Chemin(String adrDep,Double latitudeDep , Double longitudeDep, String adrArr,Double latitudeArr , Double longitudeArr, String dateDep, String dateArr, String heureDep, String heureArr) {
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
}
