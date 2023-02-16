package com.example.wassali;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wassali.Chemins.DeclarerActivity;
import com.example.wassali.Chemins.MescheminsActivity;
import com.example.wassali.Chemins.RechercheActivity;
import com.example.wassali.DmLivBenevole.DemandelivraisonActivity;
import com.example.wassali.Chemins.MesdemandesActivity;
import com.example.wassali.LivBenevole.MeslivraisonsActivity;
import com.example.wassali.LivClient.SuiviMesLivraisonActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref = db.collection("User").document(FirebaseAuth.getInstance().getUid());


    Animation topAnim,botAnim;
    ImageView image;
    TextView logo,slogon,userName;
    ImageButton arrow;


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null ){
             loadUserInfo();
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadUserInfo(){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                          //  System.out.println(documentSnapshot.getString("nomprenom"));
                            userName.setText(documentSnapshot.getString("nomprenom"));
                        }
                    }
                });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.logoimage);
        logo = findViewById(R.id.titre);
        slogon = findViewById(R.id.slogan);
        arrow = findViewById(R.id.imageButton);



        image.setAnimation(topAnim);
        logo.setAnimation(botAnim);
        slogon.setAnimation(botAnim);
        arrow.setAnimation(botAnim);



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.userName);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.open_menu , R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.nav_home:
                        Log.d( "tag_menu", "onNavigationItemSelected: Home page");
                        Intent i1 = new Intent(MainActivity.this,MainActivity.class);
                        MainActivity.this.startActivity(i1);
                        break;
                    case R.id.modifier:
                        Log.d( "tag_menu", "onNavigationItemSelected: edit info page");
                        Intent i2 = new Intent(MainActivity.this,CompteActivity.class);
                        MainActivity.this.startActivity(i2);
                        break;
                    case R.id.recherche:
                       // loadUserInfo();
                        Log.d( "tag_menu", "onNavigationItemSelected: recherche page");
                        Intent i3 = new Intent(MainActivity.this, RechercheActivity.class);
                        MainActivity.this.startActivity(i3);
                        break;
                    case R.id.client_demande:
                        Log.d( "tag_menu", "onNavigationItemSelected: recherche page");
                        Intent i4 = new Intent(MainActivity.this, MesdemandesActivity.class);
                        MainActivity.this.startActivity(i4);
                        break;
                    case R.id.client_livraison:
                        Log.d( "tag_menu", "onNavigationItemSelected: Suivi Mes livraisons page");
                        Intent i5 = new Intent(MainActivity.this, SuiviMesLivraisonActivity.class);
                        MainActivity.this.startActivity(i5);
                        break;
                    case R.id.declarer:
                        Log.d( "tag_menu", "onNavigationItemSelected: Declarer page");
                        Intent i6 = new Intent(MainActivity.this, DeclarerActivity.class);
                        MainActivity.this.startActivity(i6);
                        break;
                    case R.id.benevole_chemin:
                        Log.d( "tag_menu", "onNavigationItemSelected: Mes chemins page");
                        Intent i7 = new Intent(MainActivity.this, MescheminsActivity.class);
                        MainActivity.this.startActivity(i7);
                        break;
                    case R.id.benevole_demande:
                        Log.d( "tag_menu", "onNavigationItemSelected: Mes chemins page");
                        Intent i8 = new Intent(MainActivity.this, DemandelivraisonActivity.class);
                        MainActivity.this.startActivity(i8);
                        break;
                    case R.id.benevole_livraison:
                        Log.d( "tag_menu", "onNavigationItemSelected: Mes chemins page");
                        Intent i9 = new Intent(MainActivity.this, MeslivraisonsActivity.class);
                        MainActivity.this.startActivity(i9);
                        break;

                    case R.id.deconnecter:
                        Log.d( "tag_menu", "onNavigationItemSelected: Deconnecter");
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent i10 = new Intent(MainActivity.this, LogIn.class);
                        MainActivity.this.startActivity(i10);
                        break;

                }
                return true;
            }
        });

    }
    public void scrollToTextView(View view) {
        TextView textView = findViewById(R.id.text_view);
        textView.clearFocus();
        Log.d("Button", "scrollToTextView called");
        textView.requestFocus();
    }
}