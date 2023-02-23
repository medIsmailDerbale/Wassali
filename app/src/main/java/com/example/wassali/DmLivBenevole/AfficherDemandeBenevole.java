package com.example.wassali.DmLivBenevole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wassali.Chemins.Chemin;
import com.example.wassali.Chemins.Demande;
import com.example.wassali.Chemins.Livraison;
import com.example.wassali.R;
import com.example.wassali.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AfficherDemandeBenevole extends AppCompatActivity {

    TextView addDep , addArr , dateDep , dateArr , nomprenom , numtel , desc , poids , taille ;
    Button buttonAccepter , buttonRefuser ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query refD = db.collection("Demande");
    Query refC = db.collection("Chemin");
    CollectionReference refU = db.collection("User");

    DocumentReference demandeRef ;

    String userID,cheminID;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demandelivbenevole);
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");

        addDep = findViewById(R.id.text_point_depart);
        addArr = findViewById(R.id.text_point_arrivee);
        dateDep = findViewById(R.id.text_date_depart);
        dateArr = findViewById(R.id.text_date_arrivee);
        nomprenom = findViewById(R.id.text_nom);
        numtel = findViewById(R.id.text_numero_tel);
        desc = findViewById(R.id.text_desc);
        poids = findViewById(R.id.text_poids);
        taille = findViewById(R.id.text_taille);
        buttonAccepter = findViewById(R.id.buttonAccepter);
        buttonRefuser = findViewById(R.id.buttonRefuser);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //recuperer tout les info de la demande à partir de ce ID et les afficher

        refD.whereEqualTo("demandeID", ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    Demande demande = documentSnapshot.toObject(Demande.class);
                    cheminID = demande.getCheminID();
                    userID = demande.getUserID();
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



                            }
                        }
                    });

                    refU.document(demande.getUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

        buttonAccepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Livraison").add(new Livraison(firebaseFirestore.collection("Livraison").document().getId(),ID,cheminID,userID));
                refD.whereEqualTo("demandeID", ID).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Demande").document(document.getId()).update("etat", "acceptée")
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Demande Acceptée"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });



            }
        });

        buttonRefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                refD.whereEqualTo("DemandeID", ID).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection("Demande").document(document.getId()).update("etat", "refusée")
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Demande Refusée"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

            }
        });

        setTitle("Information sur la demande de livraison");
    }

}
