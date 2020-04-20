package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Truck> searchResults;
    private Context context;

    public SearchAdapter(ArrayList<Truck> trucks, Context context) {
        searchResults = trucks;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_truck_card,parent,false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.truckNameTextView.setText(searchResults.get(position).getName());
        holder.companyNameTextView.setText(searchResults.get(position).getCompany().getName());
        holder.hoursTextView.setText(searchResults.get(position).getHoursFormatted());
        holder.distanceTextView.setText(Double.toString(searchResults.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void updateResults(ArrayList<Truck> newResults) {
        searchResults = newResults;
    }



    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView truckNameTextView,companyNameTextView,hoursTextView,distanceTextView;

        public SearchViewHolder(View itemView) {
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
            intent.putExtra("com.example.curbside.truck", searchResults.get(position));
            context.startActivity(intent);
        }
    }



}
