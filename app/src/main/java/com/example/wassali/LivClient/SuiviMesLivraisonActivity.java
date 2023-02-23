package com.example.wassali.LivClient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.Chemins.RecycleViewInterface;
import com.example.wassali.DmLivBenevole.AfficherDemandeBenevole;
import com.example.wassali.DmLivBenevole.DemandelivraisonActivity;
import com.example.wassali.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SuiviMesLivraisonActivity extends AppCompatActivity implements RecycleViewInterface {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Suivi mes livraisons");
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
        demandeAdapter = new DemandeAdapter(this , SuiviMesLivraisonActivity.this , dataList);
        recyclerView.setAdapter(demandeAdapter);

        mAuth = FirebaseAuth.getInstance();
        setTitle("Information sur la livraison");

        EventChangeListener();
    }

    private void EventChangeListener() {
        final int[] iter = {0};
        livraisonRef.whereEqualTo("userID", FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    String demandeID = demandeModel.getDemandeID();
                    String etat = demandeModel.getEtat();

                    System.out.println("etat  :  "+ etat + "id demande : " +demandeID);
                    demandeRef.whereEqualTo("demandeID", demandeID).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                String desc , adrArr;
                                desc = demandeModel.getDesc();
                                dataList.get(iter[0]).setDesc(desc);
                                iter[0]++;
                                demandeList.add(documentChange.getDocument().toObject(DemandeModel.class));

                            }
                            demandeAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    });


                    dataList.add(new DemandeModel("null",etat,"null"));
                    demandeList.add(documentChange.getDocument().toObject(DemandeModel.class));

                }

                for (DemandeModel demandeModel : dataList) {
                    System.out.println("desc : " + demandeModel.getDesc() +  " etat : " + demandeModel.getEtat());
                }
                demandeAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();


            }
        });
    }

    public void AfficherLivraison(View v)
    {
        Intent i = new Intent(this, AfficherLivraison.class);
        Log.d("testaff", "Afficher: ");
        this.startActivity(i);
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(SuiviMesLivraisonActivity.this, AfficherLivraison.class);
        i.putExtra("ID" , demandeList.get(position).demandeID);
        System.out.println("demandeID : " + demandeList.get(position).demandeID);
        Log.d("testaff", "Afficher: ");
        SuiviMesLivraisonActivity.this.startActivity(i);
    }
}
