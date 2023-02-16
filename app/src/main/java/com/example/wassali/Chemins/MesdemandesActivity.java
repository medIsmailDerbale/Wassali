package com.example.wassali.Chemins;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.DmLivClient.AfficherDemandeActivity;
import com.example.wassali.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MesdemandesActivity extends AppCompatActivity implements RecycleViewInterface {

    RecyclerView recyclerView;
    DemandeAdapter demandeAdapter;
    ArrayList<DemandeModel> demandeList;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query ref = db.collection("Demande").whereEqualTo("userID" , FirebaseAuth.getInstance().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recycle_view);
        setTitle("Mes demandes de livraison");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        demandeList = new ArrayList<DemandeModel>();
        demandeAdapter = new DemandeAdapter(this , MesdemandesActivity.this , demandeList);
        recyclerView.setAdapter(demandeAdapter);

        mAuth = FirebaseAuth.getInstance();
        EventChangeListener();
    }

    private void EventChangeListener() {

        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error",error.getMessage());
                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()){
                    demandeList.add(documentChange.getDocument().toObject(DemandeModel.class));
                }

                demandeAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();


            }
        });
    }



    public void Afficher(View v)
    {
        Intent i = new Intent(MesdemandesActivity.this, com.example.wassali.DmLivClient.AfficherDemandeActivity.class);
        Log.d("testaff", "Afficher: ");
        MesdemandesActivity.this.startActivity(i);

    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(MesdemandesActivity.this, AfficherDemandeActivity.class);
        i.putExtra("ID" , demandeList.get(position).DemandeID);
        Log.d("testaff", "Afficher: ");
        MesdemandesActivity.this.startActivity(i);
    }
}
