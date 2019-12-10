package com.example.homefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SellerDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash);
    }

    public void uploadHome(View v){
        Intent intent = new Intent(SellerDash.this,SellerUpload.class);
        startActivity(intent);
    }

    public void requestsPage(View v){
        Intent intent = new Intent(SellerDash.this,Requests.class);
        startActivity(intent);
    }
}
