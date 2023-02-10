package com.example.wassali.Chemins;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

public class MesRecherchesActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView depart , arrivee ;
    ArrayList<CheminModel> cheminList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        db = FirebaseFirestore.getInstance();
        setTitle("Resultat de recherche");

        depart = findViewById(R.id.departR);
        arrivee = findViewById(R.id.arriveeR);

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


    }


    public void recherche(double latitudeDep, double latitudeArr, double longitudeDep, double longitudeArr){
        Query query = db.collection("Chemin").whereEqualTo("latitudeDep",latitudeDep)
                .whereEqualTo("latitudeArr",latitudeArr)
                .whereEqualTo("longitudeDep",longitudeDep)
                .whereEqualTo("longitudeArr",longitudeArr);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                    System.out.println("latitude dep : " + documentSnapshot.getString("adrDep") + "arr " + documentSnapshot.getString("adrArr") +"date" +
                            documentSnapshot.getString("dateDep"));
                }
            }
        });

    }


    private static final int EARTH_RADIUS = 6371; // Radius of the earth in km

    public void research(double startLat, double startLong, double endLat, double endLong) {
        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLong - startLong);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // Convert to meters

        db.collection("Chemin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()){
                    if (distance <= 5000) {
                        depart.setText(documentSnapshot.getString("adrDep"));
                        arrivee.setText(documentSnapshot.getString("adrArr"));
                        System.out.println("depart: "+ documentSnapshot.getString("adrDep") +"    arrivee: "+documentSnapshot.getString("adrArr"));
                    }
                }
            }
        });

    }

    public void Afficher(View v)
    {
        Intent i = new Intent(MesRecherchesActivity.this, com.example.wassali.Chemins.AfficherRechercheActivity.class);
        Log.d("testaff", "Afficher: ");
        MesRecherchesActivity.this.startActivity(i);

    }

}
