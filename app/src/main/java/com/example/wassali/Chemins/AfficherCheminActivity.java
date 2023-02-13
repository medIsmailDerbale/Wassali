package com.example.wassali.Chemins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.DmLivClient.AfficherDemandeActivity;
import com.example.wassali.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AfficherCheminActivity extends AppCompatActivity {
    TextView depart ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemin);
        setTitle("Afficher Chemin");

        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
        System.out.println("ID is : " +ID );


        depart = findViewById(R.id.text_point_depart);
        depart.setText(ID);


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
