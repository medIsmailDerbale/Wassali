package com.example.wassali.Chemins;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MesRecherchesActivity extends AppCompatActivity implements RecycleViewInterface{

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CheminModel> cheminList;


    RecyclerView recyclerView;
    CheminAdapter cheminAdapter;
    ProgressDialog progressDialog;



    //Button consulterbutton ;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        db = FirebaseFirestore.getInstance();
        setTitle("Resultat de recherche");


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cheminList = new ArrayList<CheminModel>();
        cheminAdapter = new CheminAdapter(this , MesRecherchesActivity.this , cheminList);
        recyclerView.setAdapter(cheminAdapter);

        //consulterbutton = findViewById(R.id.ButtonConsulter);

        Intent intent = getIntent();
        String depart = intent.getStringExtra("adrDep");
        String arrivee = intent.getStringExtra("adrArr");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));


        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.getController().setZoom(7);
        map.getController().setCenter(new GeoPoint(48.8588443, 2.2943506));


        Geocoder geocoder = new Geocoder(this);
        List<Address> addressesDepart = null;
        try {
            addressesDepart = geocoder.getFromLocationName(depart, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Address> addressesArrivee = null;
        try {
            addressesArrivee = geocoder.getFromLocationName(arrivee, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double latitudeDepart = 0, longitudeDepart = 0,latitudeArrivee = 0, longitudeArrivee = 0;
        if (addressesDepart.size() > 0 && addressesArrivee.size() > 0) {
            Address addressDepart = addressesDepart.get(0);
            Address addressArrivee = addressesArrivee.get(0);
            latitudeDepart = addressDepart.getLatitude();
            longitudeDepart = addressDepart.getLongitude();
            latitudeArrivee = addressArrivee.getLatitude();
            longitudeArrivee = addressArrivee.getLongitude();

            GeoPoint geoPointDepart = new GeoPoint(latitudeDepart, longitudeDepart);
            GeoPoint geoPointArrivee = new GeoPoint(latitudeArrivee, longitudeArrivee);

            Polyline polyline = new Polyline();
            polyline.setWidth(4f);
            polyline.setColor(ContextCompat.getColor(this, R.color.black));
            polyline.setGeodesic(true);

            List<GeoPoint> geoPointList = new ArrayList<>();
            geoPointList.add(geoPointDepart);
            geoPointList.add(geoPointArrivee);

            polyline.setPoints(geoPointList);
            map.getOverlays().add(polyline);
            map.invalidate();
        }

        recherche(latitudeDepart,latitudeArrivee,longitudeDepart,longitudeArrivee);


      /*  consulterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MesRecherchesActivity.this, AfficherCheminActivity.class);
                Log.d("testaff", "Afficher: ");
                MesRecherchesActivity.this.startActivity(i);
            }
        });*/

    }


    public void recherche(double latitudeDep, double latitudeArr, double longitudeDep, double longitudeArr)
    {
        Query query = db.collection("Chemin").whereNotEqualTo("userID" ,FirebaseAuth.getInstance().getUid() )
        .whereEqualTo("latitudeDep",latitudeDep)
                .whereEqualTo("latitudeArr",latitudeArr)
                .whereEqualTo("longitudeDep",longitudeDep)
                .whereEqualTo("longitudeArr",longitudeArr);
                //.whereNotEqualTo("userID" , FirebaseAuth.getInstance().getUid());

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            if (error != null){
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e("Firestore error",error.getMessage());
                return;
            }

            for (DocumentChange documentChange : value.getDocumentChanges()){
                System.out.println("adressse : " + documentChange.getDocument().getString("adrDep") );

                cheminList.add(documentChange.getDocument().toObject(CheminModel.class));
            }

            cheminAdapter.notifyDataSetChanged();
            if (progressDialog.isShowing())
                progressDialog.dismiss();


        }
    });

    }



    public void Afficher(View v)
    {
        Intent i = new Intent(MesRecherchesActivity.this, com.example.wassali.Chemins.AfficherRechercheActivity.class);
        Log.d("testaff", "Afficher: ");
        MesRecherchesActivity.this.startActivity(i);

    }


    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(MesRecherchesActivity.this, com.example.wassali.Chemins.AfficherRechercheActivity.class);
        i.putExtra("ID" , cheminList.get(position).cheminID);
        Log.d("testaff", "Afficher: ");
        MesRecherchesActivity.this.startActivity(i);
    }
}
