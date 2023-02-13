package com.example.wassali.Chemins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AfficherRechercheActivity extends AppCompatActivity {

    EditText editTextPoids,editTextTaille , editTextDesc ;
    Button buttonAjouter;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    String ID ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultatrecherche);
        Intent i = getIntent();
        setTitle("Consulter la livraison");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

       /* editTextPoids = findViewById(R.id.edit_poids);
        editTextTaille = findViewById(R.id.edit_taille);
        editTextDesc = findViewById(R.id.edit_description);*/


        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        //Khadama pour afficher tt les info dun chemin a partir de ce ID
    }

    public void EnvoyerDemande(View v)
    {
        Intent i = new Intent(AfficherRechercheActivity.this, com.example.wassali.DmLivClient.EtabliDemandeActivity.class);
        Log.d("testaff", "Afficher: ");
        i.putExtra("ID" , ID);
        AfficherRechercheActivity.this.startActivity(i);
    }

}
