package com.example.wassali.LivClient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.Chemins.Chemin;
import com.example.wassali.Chemins.Demande;
import com.example.wassali.Chemins.Livraison;
import com.example.wassali.R;
import com.example.wassali.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class AfficherLivraison extends AppCompatActivity {

    TextView addDep , addArr , dateDep , dateArr , nomprenom , numtel , desc , poids , taille , etat;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query refL = db.collection("Livraison");
    Query refD = db.collection("Demande");
    Query refC = db.collection("Chemin");
    CollectionReference refU = db.collection("User");

    String demandeID,cheminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivimalivraison);
        Intent i = getIntent();

        String ID = i.getStringExtra("ID");

        etat = findViewById(R.id.text_etat);
        addDep = findViewById(R.id.text_point_depart);
        addArr = findViewById(R.id.text_point_arrivee);
        dateDep = findViewById(R.id.text_date_depart);
        dateArr = findViewById(R.id.text_date_arrivee);
        nomprenom = findViewById(R.id.text_nom);
        numtel = findViewById(R.id.text_numero_tel);
        desc = findViewById(R.id.text_desc);
        poids = findViewById(R.id.text_poids);
        taille = findViewById(R.id.text_taille);

        //recuperer tout les info de la demande à partir de ce ID et les afficher

        refL.whereEqualTo("idDemande",ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    Livraison livraison = documentSnapshot.toObject(Livraison.class);
                    etat.setText("Etat de la livraison : "+livraison.getEtat());
            }
            }
        });
        refD.whereEqualTo("demandeID", ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    Demande demande = documentSnapshot.toObject(Demande.class);
                    cheminID = demande.getCheminID();
                    // do something with the retrieved data
                    desc.setText("Description : "+demande.getDesc());
                    poids.setText("Poids : "+demande.getPoids());
                    taille.setText("Taille : "+demande.getTaille());

                    refC.whereEqualTo("cheminID", cheminID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                Chemin chemin = documentSnapshot.toObject(Chemin.class);

                                addDep.setText("Point de départ : "+chemin.getAdrDep());
                                addArr.setText("Point d'arrivée : "+chemin.getAdrArr());
                                dateDep.setText("Date de départ : "+chemin.getDateDep());
                                dateArr.setText("Date d'arrivée : "+chemin.getDateArr());

                                System.out.println("userID : "+chemin.getCheminID());

                                refU.document(chemin.getUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            nomprenom.setText("Nom et prénom : "+user.getNomprenom());
                                            numtel.setText("Numéro de téléphone : "+user.getPhonenumber());

                                        }
                                    }
                                });

                            }
                        }
                    });



                }
            }
        });
        setTitle("Information sur la livraison");
    }

}