package com.example.wassali.Chemins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AfficherRechercheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultatrecherche);
        Intent i = getIntent();
        setTitle("Consulter la livraison");
    }

    public void EnvoyerDemande(View v)
    {
        /*Intent i = new Intent(AfficherRechercheActivity.this, com.example.wassali.DmLivClient.EtabliDemandeActivity.class);
        Log.d("testaff", "Afficher: ");
        AfficherRechercheActivity.this.startActivity(i);*/
        new MaterialAlertDialogBuilder(AfficherRechercheActivity.this)
                .setTitle("Etablir une demande")
                .setView(R.layout.activity_etablirdemande)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

}
