package com.example.wassali.DmLivClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;

public class AfficherDemandeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mademandelivraisonclient);
        Log.d("testaffa", "Afficher: ");
        Intent i = getIntent();
        setTitle("Information sur la demande");
    }

}
