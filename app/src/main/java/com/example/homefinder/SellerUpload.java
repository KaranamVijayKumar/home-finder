package com.example.homefinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SellerUpload extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String place = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_upload);
    }
}

