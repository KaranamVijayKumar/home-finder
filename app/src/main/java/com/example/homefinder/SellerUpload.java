package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SellerUpload extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String place = null,phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_upload);


        final EditText Price = findViewById(R.id.etSellerPrice);
        final EditText Bed = findViewById(R.id.etSellerBed);
        final RadioButton Yes = findViewById(R.id.radioYes);
        final RadioButton No = findViewById(R.id.radioNo);
//        final String[] place = {""};
        final String[][] city = {getResources().getStringArray(R.array.cities)};


        db.collection("users").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    for (DocumentSnapshot doc : myListOfDocuments) {
                        if(doc.get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            phone = doc.get("phone").toString();
                        }
                    }
                }
            }
        });
        Spinner cities = findViewById(R.id.spinner);
        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                place = city[0][position];
//                Toast.makeText(getApplicationContext(),""+city[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void submit(View v) {
        final EditText Price = findViewById(R.id.etSellerPrice);
        final EditText Bed = findViewById(R.id.etSellerBed);
        final RadioButton Yes = findViewById(R.id.radioYes);
        final RadioButton No = findViewById(R.id.radioNo);
        Map<String, String> city = new HashMap<>();
        city.put("bedrooms", Bed.getText().toString());
        city.put("city", place);
        city.put("owner", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        city.put("price", Price.getText().toString());
        city.put("phone",phone);

        if (Yes.isChecked()) {
            city.put("Available", "True");
        } else {
            city.put("Available", "False");
        }


        db.collection("homes").document()
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("", "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(), "Done uploading", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SellerUpload.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error writing document", e);
                    }
                });
    }
}