package com.example.homefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
    }
}
