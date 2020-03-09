package com.example.curbside;

import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    ArrayList<Truck> favorites;

    public FavoritesAdapter(ArrayList<Truck> favorites) {
        this.favorites = favorites;
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
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView truckNameTextView,companyNameTextView,hoursTextView;

        public MyViewHolder(View itemView){
            super(itemView);

            truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);
        }
    }
}
