package com.example.wassali.DmLivBenevole;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.Chemins.CheminModel;
import com.example.wassali.Chemins.MesdemandesActivity;
import com.example.wassali.Chemins.RecycleViewInterface;


import com.example.wassali.DmLivClient.AfficherDemandeActivity;
import com.example.wassali.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DemandelivraisonActivity extends AppCompatActivity implements RecycleViewInterface {

    RecyclerView recyclerView;
    DemandeAdapter demandeAdapter;
    ArrayList<DemandeModel> demandeList;
    ArrayList<DemandeModel> dataList;

    ProgressDialog progressDialog;


    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference demandeRef = db.collection("Demande");
    CollectionReference cheminRef = db.collection("Chemin");
    Query queryC = cheminRef.whereEqualTo("userID", FirebaseAuth.getInstance().getUid());

    CollectionReference userRef = db.collection("User");
    int iter2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_demandeslivraison);
        setTitle("Demandes de livraison");

        setContentView(R.layout.my_recycle_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        demandeList = new ArrayList<DemandeModel>();
        dataList = new ArrayList<>();
        demandeAdapter = new DemandeAdapter(this , DemandelivraisonActivity.this , dataList);
        recyclerView.setAdapter(demandeAdapter);

        mAuth = FirebaseAuth.getInstance();

        EventChangeListener();


    }

    private void EventChangeListener() {
        final int[] iter = {0};

        queryC.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error",error.getMessage());
                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()){
                    DemandeModel demandeModel = documentChange.getDocument().toObject(DemandeModel.class);
                    String cheminID = demandeModel.getCheminID();
                    System.out.println(cheminID);
                    demandeRef.whereEqualTo("cheminID", cheminID).whereEqualTo("etat" , "en attente").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                Log.e("Firestore error",error.getMessage());
                                return;
                            }

                            for (DocumentChange documentChange : value.getDocumentChanges()){
                                DemandeModel demandeModel = documentChange.getDocument().toObject(DemandeModel.class);
                                dataList.add(new DemandeModel("null","null","null",cheminID,"null","null"));
                                String desc , userID ,etat;
                                desc = demandeModel.getDesc();
                                System.out.println("colis : " + desc);
                                userID = demandeModel.getUserID();
                                System.out.println("userID : " + userID);
                                etat = demandeModel.getEtat();
                                userRef.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            DemandeModel demandeModel = documentSnapshot.toObject(DemandeModel.class);
                                            String nomprenom ;
                                            nomprenom = demandeModel.getNomprenom();
                                            System.out.println("non : " + nomprenom);
                                            dataList.get(iter2).setNomprenom(nomprenom);
                                            System.out.println("iter 2 : " + iter2);
                                            demandeAdapter.notifyDataSetChanged();
                                            iter2++;
                                            for (DemandeModel demandeModel1 : dataList) {
                                                System.out.println("nomprenom : " + demandeModel1.getNomprenom() + "  Colis : " + demandeModel1.getDesc() + " id chemin : " + demandeModel1.cheminID);
                                            }


                                        }

                                        demandeAdapter.notifyDataSetChanged();

                                    }

                                });
                                dataList.get(iter[0]).setEtat(etat);
                                dataList.get(iter[0]).setDesc(desc);
                                System.out.println("iterator : " + iter[0]);

                                iter[0]++;
                                demandeList.add(documentChange.getDocument().toObject(DemandeModel.class));

                            }



                            for (DemandeModel demandeModel1 : dataList) {
                                System.out.println("LAST ONE nomprenom : " + demandeModel1.getNomprenom() + "  Colis : " + demandeModel1.getDesc() + " id chemin : " + demandeModel1.cheminID);
                            }

                           // demandeAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    });




                }


//                for (DemandeModel demandeModel1 : dataList) {
//                    System.out.println("LAST ONE nomprenom : " + demandeModel1.getNomprenom() + "  Colis : " + demandeModel1.getDesc() + " id chemin : " + demandeModel1.cheminID);
//                }
                demandeAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();


            }
        });
    }

    public void AfficherUneDemande(View v)
    {
        Intent i = new Intent(this, AfficherDemandeBenevole.class);
        Log.d("testaff", "Afficher: ");
        this.startActivity(i);
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(DemandelivraisonActivity.this, AfficherDemandeBenevole.class);
        i.putExtra("ID" , demandeList.get(position).demandeID);
        System.out.println("demandeID : " + demandeList.get(position).demandeID);
        Log.d("testaff", "Afficher: ");
        DemandelivraisonActivity.this.startActivity(i);
    }
}
