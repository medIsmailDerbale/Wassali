package com.example.wassali.Chemins;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.wassali.LogIn;
import com.example.wassali.MapActivity;
import com.example.wassali.R;
import com.example.wassali.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeclarerActivity extends AppCompatActivity {
    EditText editTextAdrDep,editTextAdrArr , editTextDateDep , editTextDateArr
            , editTextHeureDep , editTextHeureArr ;
    Button buttonAjouter;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declarer);
        setTitle("Declarer un chemin");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        buttonAjouter = findViewById(R.id.buttonAjouter);
        editTextAdrDep = findViewById(R.id.edit_depart);
        editTextAdrArr = findViewById(R.id.edit_arrivee);
        editTextDateDep = findViewById(R.id.edit_date_debut);
        editTextDateArr = findViewById(R.id.edit_date_arrivee);
        editTextHeureDep = findViewById(R.id.edit_heure_depart);
        editTextHeureArr = findViewById(R.id.edit_heure_arrivee);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

//        MapView mapView = (MapView) findViewById(R.id.map);
//        mapView.setTileSource(TileSourceFactory.MAPNIK);
//        mapView.setBuiltInZoomControls(true);
//        mapView.setMultiTouchControls(true);


        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID,adrDep, adrArr, dateDep, dateArr, heureDep, heureArr;

                double latitudeDep = 0, longitudeDep = 0,latitudeArr = 0, longitudeArr = 0;
                adrDep = editTextAdrDep.getText().toString();
                adrArr = editTextAdrArr.getText().toString();
                dateDep = editTextDateDep.getText().toString();
                dateArr = editTextDateArr.getText().toString();
                heureDep = editTextHeureDep.getText().toString();
                heureArr = editTextHeureArr.getText().toString();

                if (!adrDep.equals("") && !adrArr.equals("") && !dateDep.equals("") && !dateArr.equals("") && !heureDep.equals("") && !heureArr.equals("")) {
                    // progressBar.setVisibility(View.VISIBLE);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(adrDep, 1);
                        if (addresses.size() > 0) {
                            latitudeDep = addresses.get(0).getLatitude();
                            longitudeDep = addresses.get(0).getLongitude();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(adrArr, 1);
                        if (addresses.size() > 0) {
                            latitudeArr = addresses.get(0).getLatitude();
                            longitudeArr = addresses.get(0).getLongitude();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    // chemin.add(new Chemin(adrDep,latitudeDep ,longitudeDep , adrArr,latitudeArr,longitudeArr,  dateDep, dateArr, heureDep, heureArr));
                    firebaseFirestore.collection("Chemin").add(new Chemin(userID,adrDep,latitudeDep ,longitudeDep , adrArr,latitudeArr,longitudeArr,  dateDep, dateArr, heureDep, heureArr));
                    Intent intent =new Intent(getApplicationContext(),MescheminsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Chemin ajouté", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
