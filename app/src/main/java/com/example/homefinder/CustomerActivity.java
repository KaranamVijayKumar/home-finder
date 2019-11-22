package com.example.homefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        final EditText ETcusEmail = findViewById(R.id.ETCusEmail);
        final EditText ETcusPass = findViewById(R.id.ETCusPass);
        firebaseAuth = FirebaseAuth.getInstance();
        btnCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
