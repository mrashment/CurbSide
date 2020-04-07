package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class SeeTrucksCardAdapter extends RecyclerView.Adapter<SeeTrucksCardAdapter.ViewHolder>{

    ArrayList<Truck> trucks;
    GoogleMap googleMap;
    Context context;

    public SeeTrucksCardAdapter(ArrayList<Truck> trucks, GoogleMap googleMap, Context context) {
        this.trucks = trucks;
        this.googleMap = googleMap;
        this.context = context;
    }

    @NonNull
    @Override
    public SeeTrucksCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_trucks_truck_card,parent,false);

        return new SeeTrucksCardAdapter().ViewHolder(v, this.context);

    }

    @Override
    public void onBindViewHolder(@NonNull SeeTrucksCardAdapter.ViewHolder holder, int position) {
        holder.truckNameTextView.setText(trucks.get(position).getName());
        holder.companyNameTextView.setText(trucks.get(position).getCompany().getName());
        holder.hoursTextView.setText(trucks.get(position).getHours());
    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView truckNameTextView,companyNameTextView,hoursTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.itemView.setOnClickListener(this);
            this.truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            this.companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            this.hoursTextView = itemView.findViewById(R.id.hoursTextView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.context = context;

        }

        private Context context;
        @Override
        public void onClick(View v) {

            int position = this.getLayoutPosition();

            Intent intent = new Intent((context), EditTruckInterface.class);
            intent.putExtra("com.example.curbside.truck", trucks.get(position));
            context.startActivity(intent);
        }
    }

}
