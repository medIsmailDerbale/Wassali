package com.example.wassali.LivBenevole;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.Chemins.RecycleViewInterface;
import com.example.wassali.DmLivBenevole.DemandeAdapter;
import com.example.wassali.DmLivBenevole.DemandeModel;
import com.example.wassali.DmLivBenevole.DemandelivraisonActivity;
import com.example.wassali.LivClient.AfficherLivraison;
import com.example.wassali.LivClient.SuiviMesLivraisonActivity;
import com.example.wassali.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MeslivraisonsActivity extends AppCompatActivity implements RecycleViewInterface {


    RecyclerView recyclerView;
    DemandeAdapter demandeAdapter;
    ArrayList<DemandeModel> demandeList;
    ArrayList<DemandeModel> dataList;

    ProgressDialog progressDialog;


    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference livraisonRef = db.collection("Livraison");
    CollectionReference demandeRef = db.collection("Demande");
    CollectionReference cheminRef = db.collection("Chemin");
    Query queryC = cheminRef.whereEqualTo("userID", FirebaseAuth.getInstance().getUid());

    CollectionReference userRef = db.collection("User");
    int iter,iter2,iter3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_meslivraisons);
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
        demandeAdapter = new DemandeAdapter(this , MeslivraisonsActivity.this , dataList);
        recyclerView.setAdapter(demandeAdapter);

        mAuth = FirebaseAuth.getInstance();

        EventChangeListener();
        setTitle("Mes livraisons");
    }


    private void EventChangeListener() {


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

                    livraisonRef.whereEqualTo("cheminID", cheminID).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                String etat , demandeID;
                                etat = demandeModel.getEtat();
                                demandeID = demandeModel.getDemandeID();
                                dataList.get(iter3).setEtat(etat);
                                System.out.println("iterator : " + iter3 + "id demande : "+ demandeID);
                                System.out.println("etat : " + etat);

                                iter3++;
                                demandeList.add(documentChange.getDocument().toObject(DemandeModel.class));
                                demandeRef.whereEqualTo("demandeID", demandeID).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                            //dataList.add(new DemandeModel("null","null","null",cheminID,"null","null"));
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
                                            // dataList.get(iter[0]).setEtat(etat);
                                            dataList.get(iter).setDesc(desc);
                                            System.out.println("iterator : " + iter);

                                            iter++;
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
                            for (DemandeModel demandeModel1 : dataList) {
                                System.out.println("LAST ONE gg nomprenom : " + demandeModel1.getNomprenom() + "  Colis : " + demandeModel1.getDesc() + " id chemin : " + demandeModel1.cheminID);
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


    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(MeslivraisonsActivity.this, AfficherLivraisonBenevole.class);
        i.putExtra("ID" , demandeList.get(position).demandeID);
        System.out.println("demandeID : " + demandeList.get(position).demandeID);
        Log.d("testaff", "Afficher: ");
        MeslivraisonsActivity.this.startActivity(i);
    }
}