package com.example.curbside;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    private ArrayList<Truck> favorites;
    private Context context;
    public FavoritesAdapter(ArrayList<Truck> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_truck_card,parent,false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.MyViewHolder holder, int position) {
        holder.truckNameTextView.setText(favorites.get(position).getName());
        holder.companyNameTextView.setText(favorites.get(position).getCompany().getName());
        holder.hoursTextView.setText(favorites.get(position).getHours());
        String sdistance = favorites.get(position).getDistance() == -1.0 ? "" : favorites.get(position).getDistance().toString() + " miles away";
        holder.distanceTextView.setText(sdistance);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView truckNameTextView,companyNameTextView,hoursTextView,distanceTextView;

        public MyViewHolder(View itemView){
            super(itemView);

            truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);
            distanceTextView = itemView.findViewById(R.id.distanceTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = this.getLayoutPosition();

            Intent intent = new Intent((context), TruckMenuActivity.class);
            intent.putExtra("com.example.curbside.truck", favorites.get(position));
            context.startActivity(intent);
        }
    }
}
