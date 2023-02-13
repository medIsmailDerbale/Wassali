package com.example.wassali.Chemins;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MescheminsActivity extends AppCompatActivity implements RecycleViewInterface{

    RecyclerView recyclerView;
    CheminAdapter cheminAdapter;
    ArrayList<CheminModel> cheminList;
    TextView  depart , arrivee ;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query ref = db.collection("Chemin").whereEqualTo("userID" , FirebaseAuth.getInstance().getUid()).orderBy("adrDep", Query.Direction.ASCENDING);

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null ){
            loadCheminDeclare();
        }
    }

    private void loadCheminDeclare(){

        db.collection("Chemin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()){
//                    depart.setText(documentSnapshot.getString("adrDep"));
//                    arrivee.setText(documentSnapshot.getString("adrArr"));
                    System.out.println("depart: "+ documentSnapshot.getString("adrDep") +"    arrivee: "+documentSnapshot.getString("adrArr"));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recycle_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cheminList = new ArrayList<CheminModel>();
        cheminAdapter = new CheminAdapter(this, MescheminsActivity.this , cheminList);
        recyclerView.setAdapter(cheminAdapter);

        mAuth = FirebaseAuth.getInstance();
        setTitle("Mes chemins");

//        depart = findViewById(R.id.mes_depart);
//        arrivee = findViewById(R.id.mes_arrivee);

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

                    cheminList.add(documentChange.getDocument().toObject(CheminModel.class));
                }

                cheminAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();


            }
        });
    }


    @Override
    public void onItemClick(int position) {

    }
}
