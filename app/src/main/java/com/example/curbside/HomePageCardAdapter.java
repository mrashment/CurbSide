package com.example.curbside;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePageCardAdapter extends RecyclerView.Adapter<HomePageCardAdapter.ViewHolder> {

    ArrayList<Truck> trucks;

    public HomePageCardAdapter(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    @NonNull
    @Override
    public HomePageCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_truck_card,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageCardAdapter.ViewHolder holder, int position) {
        holder.truckNameTextView.setText(trucks.get(position).getName());
        holder.companyNameTextView.setText(trucks.get(position).getCompany().getName());
        holder.hoursTextView.setText(trucks.get(position).getHours());
    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView truckNameTextView,companyNameTextView,hoursTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            this.companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            this.hoursTextView = itemView.findViewById(R.id.hoursTextView);
            this.imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
