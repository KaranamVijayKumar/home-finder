package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        final String priceMin = intent.getStringExtra("priceMin");
        final String priceMax = intent.getStringExtra("priceMax");
        final String bedrooms = intent.getStringExtra("bedrooms");
        final String city = intent.getStringExtra("city");
        ArrayList<Map> docs = (ArrayList<Map>) intent.getSerializableExtra("docsmap");
        final int[] count = {0};
//        final ArrayList<DocumentSnapshot> filtered = new ArrayList<>();
//        final HashMap<String, Map> docs = new HashMap<>();
//        final ArrayList<DocumentSnapshot> filtered = (ArrayList<DocumentSnapshot>) intent.getSerializableExtra("filtered");
//        try {
//            db.collection("homes").
//                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
//                        for (DocumentSnapshot doc : myListOfDocuments) {
//                            if (doc.get("city").equals(city)) {
//                                if (Integer.parseInt(doc.get("bedrooms").toString()) == Integer.parseInt(bedrooms)) {
//                                    if (Integer.parseInt(doc.get("price").toString()) <= Integer.parseInt(priceMax)
//                                            && Integer.parseInt(doc.get("price").toString()) >= Integer.parseInt(priceMin)) {
//                                        if (doc.get("Available").toString().equals("True")) {
//                                            docs.put(doc.getId(), doc.getData());
////                                            filtered.add(doc);
//                                        }
//
////                                    Toast.makeText(getApplicationContext(),doc+"",Toast.LENGTH_LONG).show();
////                                Toast.makeText(getApplicationContext(),"found",Toast.LENGTH_LONG).show();
//                                    }
//
//                                }
//                            }
////                        count[0]++;
//                        }
//
//                        Toast.makeText(getApplicationContext(), docs.size() + "", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//
//
//        if(docs.size() == 0){
//            Toast.makeText(getApplicationContext(),"No results found.",Toast.LENGTH_LONG*3).show();
//        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("fsasdasd");
//        temp.add("hjergjg");
        mAdapter = new HomeAdapter(docs);
        recyclerView.setAdapter(mAdapter);

//
//        Button btnHomeOwner = findViewById(R.id.btnHomeOwner);
//        btnHomeOwner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this,OwnersActivity.class);
//                startActivity(intent);
//
//            }
//        });
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),"Please choose all filters.",Toast.LENGTH_LONG*4).show();
//        }
    }


}

