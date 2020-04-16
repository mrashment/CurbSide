package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeeItemsAdapter extends RecyclerView.Adapter<SeeItemsAdapter.ViewHolder> {
    private static final String TAG = "SeeItemsAdapter";

    ArrayList<FoodItem> items;
    Context context;
    DbConnection conn;

    @NonNull
    @Override
    public SeeItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_items_card, parent, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return new SeeItemsAdapter.ViewHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeItemsAdapter.ViewHolder holder, int position) {
        holder.itemNameTextView.setText(items.get(position).getName());
        holder.itemPriceTextView.setText(items.get(position).getPrice());
        holder.itemDescriptionTextView.setText(items.get(position).getDescription());
        holder.itemTypeTextView.setText(items.get(position).getItem_type());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemNameTextView, itemPriceTextView, itemDescriptionTextView, itemTypeTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.itemView.setOnClickListener(this);
            this.itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            this.itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            this.itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            this.itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.context = context;

        }

    }

    private Context context;
    @Override
    public void onClick(View v) {

            int position = this.getLayoutPosition();

            Intent intent = new Intent((context), AddMenuItemActivity.class);
            intent.putExtra("com.example.curbside.truck", items.get(position));
            context.startActivity(intent);
        }
    }
}
