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

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<Map> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView bedroomsTV;
        public TextView priceTV;
        public TextView ownerTV;
        public Button button;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            bedroomsTV = v.findViewById(R.id.bedroomsValue);
            priceTV = v.findViewById(R.id.priceTV);
            ownerTV = v.findViewById(R.id.ownerTV);
            button = v.findViewById(R.id.button);

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

        holder.priceTV.setText("Price: $" + doc.get("price").toString());
        holder.ownerTV.setText("Owner: " + doc.get("owner").toString());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doc.get("Phone").toString()));
                v.getContext().startActivity(intent);
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
