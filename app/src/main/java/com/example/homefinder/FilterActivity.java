package com.example.homefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final EditText minPrice = findViewById(R.id.etMinPrice);
        final EditText maxPrice = findViewById(R.id.etMaxPrice);
        final EditText bedrooms = findViewById(R.id.etBeds);
        final String[] place = {""};
        final String[][] city = {getResources().getStringArray(R.array.cities)};
        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this,HomeActivity.class);
                intent.putExtra("priceMin",minPrice.getText().toString());
                intent.putExtra("priceMax",maxPrice.getText().toString());
                intent.putExtra("city",place[0]);
                intent.putExtra("bedrooms",bedrooms.getText().toString());
                startActivity(intent);
            }
        });
        Spinner cities = findViewById(R.id.spinner);
        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                place[0] = city[0][position];
//                Toast.makeText(getApplicationContext(),""+city[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}






