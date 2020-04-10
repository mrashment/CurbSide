package com.example.curbside;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    private ArrayList<Truck> searchResults;
    private ArrayList<Truck> filterList;
    private ValueFilter valueFilter;

    public SearchAdapter(ArrayList<Truck> trucks) {
        searchResults = trucks;
        filterList = trucks;
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
        holder.hoursTextView.setText(searchResults.get(position).getHours());
        holder.distanceTextView.setText(Double.toString(searchResults.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void updateResults(ArrayList<Truck> newResults) {
        searchResults = newResults;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView truckNameTextView,companyNameTextView,hoursTextView,distanceTextView;

        public SearchViewHolder(View itemView) {
            super(itemView);

            truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);
            distanceTextView = itemView.findViewById(R.id.distanceTextView);
        }
    }

    class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    if ((filterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(filterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
