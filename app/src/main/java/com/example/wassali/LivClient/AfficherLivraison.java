package com.example.wassali.LivClient;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class AfficherLivraison extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivimalivraison);
        Intent i = getIntent();
        setTitle("Information sur la livraison");
    }

}
