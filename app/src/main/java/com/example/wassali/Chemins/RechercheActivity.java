package com.example.wassali.Chemins;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.DmLivClient.AfficherDemandeActivity;
import com.example.wassali.R;

public class RechercheActivity extends AppCompatActivity {
    EditText editTextAdrDep,editTextAdrArr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        setTitle("Recherche");
        editTextAdrDep = findViewById(R.id.edit_departR);
        editTextAdrArr = findViewById(R.id.edit_arriveeR);
    }

    public void Afficher(View v)
    {
        Intent i = new Intent(RechercheActivity.this, com.example.wassali.Chemins.MesRecherchesActivity.class);
        String adrDep = editTextAdrDep.getText().toString();
        String adrArr = editTextAdrArr.getText().toString();
        i.putExtra("adrDep", adrDep);
        i.putExtra("adrArr", adrArr);
        Log.d("testaff", "Afficher: ");
        RechercheActivity.this.startActivity(i);

    }
}
