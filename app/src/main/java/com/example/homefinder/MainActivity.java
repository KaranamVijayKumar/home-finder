package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnRegister;

    private FirebaseAuth mAuth;
    // ...
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.etEmail);
        txtPassword = findViewById(R.id.etPWD);
        btnRegister = findViewById(R.id.btnLogin);
        // Initialize Firebase Auth

        // initializeUI();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }

        });

        Button btnUserSignUp = findViewById(R.id.btnUserSignUp);
        btnUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);

            }
        });

        Button btnSellerSignUp = findViewById(R.id.btnSellerSignUp);
        btnSellerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SellerActivity.class);
                startActivity(intent);

            }
        });
    }


    private void updateUI(FirebaseUser user) {

    }

    private void loginUser() {
        final String email = "def@gmail.com"; //txtEmail.getText().toString();
        String password = "12345678"; //txtPassword.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication success."+user.getEmail(),
                                    Toast.LENGTH_SHORT).show();


                            DocumentReference docRef = db.collection("users").document(email);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            if (document.get("role").equals("CUSTOMER")) {

                                                //customer intent
                                                Intent cus = new Intent(MainActivity.this, FilterActivity.class);
                                                startActivity(cus);
                                            } else {
                                                //seller intent
                                                Intent sel = new Intent(MainActivity.this, SellerUpload.class);
                                                startActivity(sel);
                                               // Toast.makeText(MainActivity.this, "Seller", Toast.LENGTH_SHORT).show();
                                            }
                                            Log.d("", "DocumentSnapshot data: " + document.getData());
                                        } else {
                                            Log.d("", "No such document");
                                        }
                                    } else {
                                        Log.d("", "get failed with ", task.getException());
                                    }
                                }
                            });
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });


    }
}