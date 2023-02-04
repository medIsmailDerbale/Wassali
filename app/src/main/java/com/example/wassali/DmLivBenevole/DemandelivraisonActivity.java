package com.example.wassali.DmLivBenevole;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class DemandelivraisonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demandeslivraison);
        setTitle("Demandes de livraison");
    }

    public void AfficherUneDemande(View v)
    {
        Intent i = new Intent(this, AfficherDemandeBenevole.class);
        Log.d("testaff", "Afficher: ");
        this.startActivity(i);
    }
}
