package com.example.wassali.DmLivClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class AfficherDemandeActivity extends AppCompatActivity {

    TextView demandeid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mademandelivraisonclient);
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");

        //recuperer tout les info de la demande à partir de ce ID et les afficher

        demandeid = findViewById(R.id.text_etat);
        setTitle("Information sur la demande");
    }

}
