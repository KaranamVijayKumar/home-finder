package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FilterActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final EditText minPrice = findViewById(R.id.etMinPrice);
        final EditText maxPrice = findViewById(R.id.etMaxPrice);
        final EditText bedrooms = findViewById(R.id.etBeds);
        final String[] place = {""};
        final String[][] city = {getResources().getStringArray(R.array.cities)};
//        final ArrayList<DocumentSnapshot> filtered = new ArrayList<>();
        final ArrayList<Map> docs = new ArrayList<>();
        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    db.collection("homes").
                            get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (DocumentSnapshot doc : myListOfDocuments) {
                                    if (doc.get("city").equals(place[0])) {
                                        try{
                                            if (Integer.parseInt(doc.get("bedrooms").toString()) == Integer.parseInt(bedrooms.getText().toString())) {
                                                if (Integer.parseInt(doc.get("price").toString()) <= Integer.parseInt(maxPrice.getText().toString())
                                                        && Integer.parseInt(doc.get("price").toString()) >= Integer.parseInt(minPrice.getText().toString())) {
                                                    if (doc.get("Available").toString().equals("True")) {
                                                        docs.add(doc.getData());

//                                                        filtered.add(doc);
                                                    }
                                        }

//                                    Toast.makeText(getApplicationContext(),doc+"",Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(),"found",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                        catch (Exception e){
                                            Toast.makeText(getApplicationContext(),"Please choose all filters.",Toast.LENGTH_LONG*4).show();

                                        }
                                    }
                                }
                                if (docs.size() == 0){
                                    Toast.makeText(getApplicationContext(),"No results found.",Toast.LENGTH_LONG*4).show();
                                }
                                else {
                                    Intent intent = new Intent(FilterActivity.this,HomeActivity.class);
                                    intent.putExtra("priceMin",minPrice.getText().toString());
                                    intent.putExtra("priceMax",maxPrice.getText().toString());
                                    intent.putExtra("city",place[0]);
                                    intent.putExtra("bedrooms",bedrooms.getText().toString());
                                    intent.putExtra("docsmap",docs);
//                                    intent.putExtra("filtered",filtered);
                                    startActivity(intent);
                                }

                            }
                        }
                    });


                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Please choose all filters.",Toast.LENGTH_LONG*4).show();
                }
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






