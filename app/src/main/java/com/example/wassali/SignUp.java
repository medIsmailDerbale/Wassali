package com.example.wassali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRegistrar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextNomprenom , textInputEditTextEmail , textInputEditTextBirthdate , textInputEditTextPhonenumber , textInputEditTextPassword ;
    Button buttonSignUp;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;


    TextView textViewLogin ;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null ){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        textInputEditTextNomprenom = findViewById(R.id.nomprenom);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextBirthdate = findViewById(R.id.birthdate);
        textInputEditTextPhonenumber = findViewById(R.id.phonenumber);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);



        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),LogIn.class);
                startActivity(intent);
                finish();
            }
        });





        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomprenom , email , birthdate , phonenumber , password ;
                nomprenom = String.valueOf(textInputEditTextNomprenom.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                birthdate = String.valueOf(textInputEditTextBirthdate.getText());
                phonenumber = String.valueOf(textInputEditTextPhonenumber.getText());
                password = String.valueOf(textInputEditTextPassword.getText());



                if (!nomprenom.equals("") && !email.equals("") && !birthdate.equals("") && !phonenumber.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    System.out.println(nomprenom + email);
                    mAuth.createUserWithEmailAndPassword(email ,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);
                            finish();
                            Intent intent =new Intent(getApplicationContext(),LogIn.class);
                            startActivity(intent);
                            firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid())
                                    .set(new User(nomprenom,email,birthdate,phonenumber ));
                            mAuth.getCurrentUser().sendEmailVerification();
                            Toast.makeText(getApplicationContext() , "Verifier votre e-mail" , Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            });
//                {
//
//
                }else {
                    Toast.makeText(getApplicationContext(), "Tous les champs sont obligatoires" , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}