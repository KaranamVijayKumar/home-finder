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

public class CustomerActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Button btnCustomerSignUp = findViewById(R.id.btnCustomerSignUp);
        final EditText ETcusFN = findViewById(R.id.ETCusFN);
        final EditText ETcusLN = findViewById(R.id.ETCusLN);
        final EditText ETcusPH = findViewById(R.id.ETCusPH);
        final EditText ETcusEmail = findViewById(R.id.ETCusEmail);
        final EditText ETcusPass = findViewById(R.id.ETCusPass);
        firebaseAuth = FirebaseAuth.getInstance();
        btnCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fn,ln,email,pass,role,phone;
                fn = ETcusFN.getText().toString();
                ln = ETcusLN.getText().toString();
                email = ETcusEmail.getText().toString();
                pass = ETcusPass.getText().toString();
                phone = ETcusPH.getText().toString();
                role = "CUSTOMER";
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(CustomerActivity.this, new OnCompleteListener<AuthResult>() {
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
                                                    Intent intent = new Intent(CustomerActivity.this,MainActivity.class);
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
                                    // System.out.println("onComplete: Failed=" + task.getException().getMessage());
                                    Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                Intent intent = new Intent(CustomerActivity.this,MainActivity.class);
            }
        });
    }
}
