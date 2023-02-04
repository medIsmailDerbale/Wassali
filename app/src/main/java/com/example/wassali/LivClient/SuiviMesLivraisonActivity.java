package com.example.wassali.LivClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class SuiviMesLivraisonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivimeslivraison);
        setTitle("Suivi mes livraisons");
    }

    public void AfficherLivraison(View v)
    {
        Intent i = new Intent(this, AfficherLivraison.class);
        Log.d("testaff", "Afficher: ");
        this.startActivity(i);
    }
}
