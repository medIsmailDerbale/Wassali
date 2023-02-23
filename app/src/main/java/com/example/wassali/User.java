package com.example.wassali;

public class User {
    public String nomprenom , email , birthdate , phonenumber ;

    public User() {

    }

    public User(String nomprenom, String email, String birthdate, String phonenumber) {
        this.nomprenom = nomprenom;
        this.email = email;
        this.birthdate = birthdate;
        this.phonenumber = phonenumber;
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
