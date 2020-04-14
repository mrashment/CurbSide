package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SeeTrucksCardAdapter extends RecyclerView.Adapter<SeeTrucksCardAdapter.ViewHolder>{

    ArrayList<Truck> trucks;
    Context context;

    public SeeTrucksCardAdapter(ArrayList<Truck> trucks, Context context) {
        this.trucks = trucks;
        this.context = context;
    }

    @NonNull
    @Override
    public SeeTrucksCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_trucks_truck_card,parent,false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(v, this.context);

    }

    @Override
    public void onBindViewHolder(@NonNull SeeTrucksCardAdapter.ViewHolder holder, int position) {
        holder.truckNameTextView.setText(trucks.get(position).getName());
        holder.companyNameTextView.setText(trucks.get(position).getCompany().getName());
        holder.hoursTextView.setText(trucks.get(position).getHours());
        final double lng = ((SeeTrucksActivity)context).getLng();
        final double lat = ((SeeTrucksActivity)context).getLat();
        holder.broadcastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // broadcast
                } else {
                    // don't broadcast
                }
                Toast.makeText(context, "Longitude = " + lng + " Latitude = " + lat,Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private TextView truckNameTextView,companyNameTextView,hoursTextView;
        private ImageView imageView;
        private Switch broadcastSwitch;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.itemView.setOnClickListener(this);
            this.truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            this.companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            this.hoursTextView = itemView.findViewById(R.id.hoursTextView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.context = context;

//            should this be up in the "onCreate" section?
            this.broadcastSwitch = itemView.findViewById(R.id.broadcastSwitch);

            broadcastSwitch.setOnCheckedChangeListener(this);
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // broadcast
            } else {
                // don't broadcast
            }

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
