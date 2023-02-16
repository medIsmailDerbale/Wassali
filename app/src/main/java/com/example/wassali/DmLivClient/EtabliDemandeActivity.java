package com.example.wassali.DmLivClient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.Chemins.AfficherRechercheActivity;
import com.example.wassali.Chemins.Chemin;
import com.example.wassali.Chemins.Demande;
import com.example.wassali.Chemins.MescheminsActivity;
import com.example.wassali.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EtabliDemandeActivity extends AppCompatActivity {

    EditText editTextPoids,editTextTaille , editTextDesc ;
    Button buttonAjouter;

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablirdemande);
        setTitle("Etabli une Demande");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        editTextPoids = findViewById(R.id.edit_poids);
        editTextTaille = findViewById(R.id.edit_taille);
        editTextDesc = findViewById(R.id.edit_description);
        buttonAjouter = findViewById(R.id.AjouterDemande);

        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
        System.out.println("ID : " + ID);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String poids, taille , desc , userid , cheminid ;

                userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                cheminid = ID;
                poids = editTextPoids.getText().toString();
                taille = editTextTaille.getText().toString();
                desc = editTextDesc.getText().toString();


                if (!poids.equals("") && !taille.equals("") && !desc.equals("")) {
                    firebaseFirestore.collection("Demande").add(new Demande(firebaseFirestore.collection("Demande").document().getId(),poids,taille,desc,userid,cheminid));
                    Intent intent =new Intent(getApplicationContext(), AfficherRechercheActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "demande ajoutée", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void EnvoyerDemande(View v)
    {
        /*Intent i = new Intent(com.example.wassali.Chemins.AfficherRechercheActivity.this, com.example.wassali.DmLivClient.EtabliDemandeActivity.class);
        Log.d("testaff", "Afficher: ");
        com.example.wassali.Chemins.AfficherRechercheActivity.this.startActivity(i);*/
    }

}
