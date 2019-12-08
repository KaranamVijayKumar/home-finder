package com.example.homefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final EditText minPrice = findViewById(R.id.etMinPrice);
        final EditText maxPrice = findViewById(R.id.etMaxPrice);
        final EditText bedrooms = findViewById(R.id.etBeds);
        final String[] place = {""};
        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}





