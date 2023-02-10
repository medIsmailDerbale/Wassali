package com.example.wassali.Chemins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.DmLivClient.AfficherDemandeActivity;
import com.example.wassali.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AfficherCheminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemin);
        setTitle("Afficher Chemin");
    }

    public void AnnulerChemin(View v)
    {
        new MaterialAlertDialogBuilder(AfficherCheminActivity.this)
                .setTitle("Annuler le chemin")
                .setMessage("etes vous sur d'annuler votre chemin ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


}
