package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeeItemsTruckAdapter extends RecyclerView.Adapter<SeeItemsTruckAdapter.ViewHolder>{

    ArrayList<FoodItem> items;
    Context context;
    DbConnection conn;

    public SeeItemsTruckAdapter(ArrayList<FoodItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public SeeItemsTruckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_items_truck_card, parent, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return new SeeItemsTruckAdapter.ViewHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeItemsTruckAdapter.ViewHolder holder, int position) {
        holder.itemNameTextView1.setText(items.get(position).getName());
        holder.itemPriceTextView1.setText(Double.toString(items.get(position).getPrice()));
        holder.itemDescriptionTextView1.setText(items.get(position).getDescription());
        holder.itemTypeTextView1.setText(items.get(position).getItem_type());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTextView1, itemPriceTextView1, itemDescriptionTextView1, itemTypeTextView1;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.itemNameTextView1 = itemView.findViewById(R.id.itemNameTextView1);
            this.itemPriceTextView1 = itemView.findViewById(R.id.itemPriceTextView1);
            this.itemDescriptionTextView1 = itemView.findViewById(R.id.itemDescriptionTextView1);
            this.itemTypeTextView1 = itemView.findViewById(R.id.itemTypeTextView1);
        }
    }
}
