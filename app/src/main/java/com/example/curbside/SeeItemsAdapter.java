package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public SeeItemsAdapter(ArrayList<FoodItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


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
        holder.itemPriceTextView.setText(Double.toString(items.get(position).getPrice()));
        holder.itemDescriptionTextView.setText(items.get(position).getDescription());
        holder.itemTypeTextView.setText(items.get(position).getItem_type());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemNameTextView, itemPriceTextView, itemDescriptionTextView, itemTypeTextView;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.itemView.setOnClickListener(this);
            this.itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            this.itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            this.itemDescriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            this.itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);

            //TODO Need item ID to pass for "dropitem.php". Do we need another thread?

            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(this);


        }



        @Override
        public void onClick(View v) {

//            {
//                DropItemThread thread = new DropItemThread(DropItemThread.get(pos).getId());
//                thread.execute();
//                Log.d(TAG, "onCheckedChanged: item_id = " + items.getId());
//            }

            int position = this.getLayoutPosition();

            //TODO need to pass item ID so that we can populate the "AddMenuItemActivity".
            Intent intent = new Intent((context), AddMenuItemActivity.class);
            intent.putExtra("com.example.curbside.truck", items.get(position));
            context.startActivity(intent);

        }
    }
}
