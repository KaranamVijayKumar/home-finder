package com.example.homefinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<Map> values;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView bedroomsTV;
        public TextView priceTV;
        public TextView ownerTV;
        public TextView cityTV;

        public Button button, sendreq;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            bedroomsTV = v.findViewById(R.id.bedroomsValue);
            priceTV = v.findViewById(R.id.priceTV);
            ownerTV = v.findViewById(R.id.ownerTV);
            button = v.findViewById(R.id.button);
            sendreq = v.findViewById(R.id.sendreq);
            cityTV = v.findViewById(R.id.cityFiltered);
        }
    }


//    public void add(int position, String item) {
//        values.add(position, item);
//        notifyItemInserted(position);
//    }

//    public void remove(int position) {
//        values.remove(position);
//        notifyItemRemoved(position);
//    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(ArrayList<Map> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.home_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Map doc = values.get(position);
        holder.bedroomsTV.setText("Bedrooms: " + doc.get("bedrooms").toString());
//        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(position);
//            }
//        });
        holder.cityTV.setText("City: " + doc.get("city").toString());

        holder.priceTV.setText("Price: $" + doc.get("price").toString());
        holder.ownerTV.setText("Owner: " + doc.get("owner").toString());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doc.get("phone").toString()));
                v.getContext().startActivity(intent);
            }
        });
        holder.sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                mAuth = FirebaseAuth.getInstance();
                final String[] username = new String[1];
                final String[] phone = new String[1];
                db.collection("users").
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for (DocumentSnapshot daku : myListOfDocuments) {
                                if (daku.get("email").toString().equals(mAuth.getCurrentUser().getEmail())) {
                                    username[0] = daku.get("name").toString();
                                    phone[0] = daku.get("phone").toString();
                                }
                            }


                            Map<String, Object> request = new HashMap<>();
                            request.put("city", doc.get("city").toString());
                            request.put("username", username[0]);
                            request.put("phone", phone[0]);
                            request.put("owner", doc.get("owner").toString());

                            db.collection("requests").document()
                                    .set(request)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("", "DocumentSnapshot successfully written!");
                                            Toast.makeText(v.getContext(), "Request sent to owner.", Toast.LENGTH_LONG * 3).show();
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
                });

            }

        });
        Log.d("TAG", "Done");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
