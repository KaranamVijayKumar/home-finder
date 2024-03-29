package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SellerActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        Button btnSellerSignUp = findViewById(R.id.btnSellerSignUp);

        final EditText ETsellFN = findViewById(R.id.ETSellFN);
        final EditText ETsellLN = findViewById(R.id.ETSellLN);
        final EditText ETsellEmail = findViewById(R.id.ETSellEmail);
        final EditText ETsellPass = findViewById(R.id.ETSellPass);
        final EditText ETsellPhone = findViewById(R.id.etPhoneNumber);

        firebaseAuth = FirebaseAuth.getInstance();
        btnSellerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fn,ln,email,pass,role,phone;
                fn = ETsellFN.getText().toString();
                ln = ETsellLN.getText().toString();
                email = ETsellEmail.getText().toString();
                pass = ETsellPass.getText().toString();
                phone = ETsellPhone.getText().toString();
                role = "SELLER";

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(SellerActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                                    // Create a new user with a first and last name
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", fn+" "+ln);
                                    user.put("email", email);
                                    user.put("role",role);
                                    user.put("phone",phone);

                                    db.collection("users").document(email)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("", "DocumentSnapshot successfully written!");
                                                    Intent intent = new Intent(SellerActivity.this,MainActivity.class);
                                                    intent.putExtra("phone",phone);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("", "Error writing document", e);
                                                }
                                            });

                                } else {

                                    Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                Intent intent = new Intent(SellerActivity.this,MainActivity.class);
            }
        });
    }
}

