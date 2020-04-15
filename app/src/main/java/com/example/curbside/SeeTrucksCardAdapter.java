package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
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

public class SeeTrucksCardAdapter extends RecyclerView.Adapter<SeeTrucksCardAdapter.ViewHolder> {
    private static final String TAG = "SeeTrucksCardAdapter";

    ArrayList<Truck> trucks;
    Context context;
    DbConnection conn;
    private double lng;
    private double lat;

    public SeeTrucksCardAdapter(ArrayList<Truck> trucks, Context context) {
        this.trucks = trucks;
        this.context = context;
        conn = DbConnection.getInstance();
        lat = ((SeeTrucksActivity)context).getLat();
        lng = ((SeeTrucksActivity)context).getLng();
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
        if (trucks.get(position).getLat() == null) {
            holder.broadcastSwitch.setChecked(false);
        } else {
            holder.broadcastSwitch.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return trucks.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            broadcastSwitch.setOnClickListener(this);
        }


        private Context context;
        @Override
        public void onClick(View v) {
            if (v instanceof Switch) {
                boolean isChecked = ((Switch) v).isChecked();
                int pos = this.getLayoutPosition();
                if (isChecked) {
                    BroadcastThread thread = new BroadcastThread(BroadcastThread.Operation.START,trucks.get(pos).getId());
                    thread.execute(lat,lng);
                    Log.d(TAG, "onCheckedChanged: truck_id = " + trucks.get(pos).getId());
                } else {
                    BroadcastThread thread = new BroadcastThread(BroadcastThread.Operation.STOP,trucks.get(pos).getId());
                    thread.execute();
                    Log.d(TAG, "onCheckedChanged: truck_id = " + trucks.get(pos).getId());

                }
                Toast.makeText(context, "Longitude = " + lng + " Latitude = " + lat,Toast.LENGTH_LONG).show();
            } else {

                int position = this.getLayoutPosition();

                Intent intent = new Intent((context), EditTruckInterface.class);
                intent.putExtra("com.example.curbside.truck", trucks.get(position));
                context.startActivity(intent);
            }
        }


    }

}
