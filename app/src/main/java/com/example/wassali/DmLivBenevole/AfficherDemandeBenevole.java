package com.example.wassali.DmLivBenevole;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class AfficherDemandeBenevole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demandelivbenevole);
        Intent i = getIntent();
        setTitle("Information sur la demande de livraison");
    }

}
