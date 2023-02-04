package com.example.wassali.DmLivClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class MesdemandesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesdemandes);
        setTitle("Mes demandes de livraison");
    }

    public void Afficher(View v)
    {
        Intent i = new Intent(MesdemandesActivity.this, com.example.wassali.DmLivClient.AfficherDemandeActivity.class);
        Log.d("testaff", "Afficher: ");
        MesdemandesActivity.this.startActivity(i);

    }
}
