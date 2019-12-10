package com.example.homefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Requests extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        final ArrayList<Map> docs = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        db.collection("requests").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    for (DocumentSnapshot doc : myListOfDocuments) {
                        if(doc.get("owner").toString().equals(mAuth.getCurrentUser().getEmail())){
                            docs.add(doc.getData());
                        }
                    }

                    if (docs.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No results found.", Toast.LENGTH_LONG * 4).show();
                    } else {
                        recyclerView = (RecyclerView)findViewById(R.id.requestRV);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        mAdapter = new RequestsAdapter(docs);
                        recyclerView.setAdapter(mAdapter);

                    }

                }


            }
        });


    }
}