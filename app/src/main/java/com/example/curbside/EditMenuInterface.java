package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditMenuInterface extends AppCompatActivity {

    private static final String TAG = "EditMenuInterface";
    static Handler handler;
    private ArrayList<FoodItem> items;
    private RecyclerView recyclerView2;
    private SeeItemsAdapter cardAdapter2;
    private Button backToVendorOptions, applyMenuChange, addItemButton;

    protected void onResume() {
        super.onResume();
        new SeeItemsThread(this).execute(DbConnection.getInstance().getUser().getCompanyID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_interface);

        backToVendorOptions = findViewById(R.id.backToVendorOptions);
        applyMenuChange = findViewById(R.id.applyMenuChange);
        addItemButton = findViewById(R.id.addItemButton);
        backToVendorOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView2 = findViewById(R.id.recyclerView2);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO need to pass company id to add an item to the right company/vendor
                Intent intent = new Intent(EditMenuInterface.this, AddMenuItemActivity.class);
                startActivity(intent);
            }
        });

        applyMenuChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                }
        });
    }


    public void displayItems(ArrayList<FoodItem> fromThread) {
        items = fromThread;
        if (items != null) {
            recyclerView2.setVisibility(RecyclerView.VISIBLE);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            cardAdapter2 = new SeeItemsAdapter(items, this);
            recyclerView2.setAdapter(cardAdapter2);
            recyclerView2.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));

        } else {
            Toast.makeText(this,"No Company Items",Toast.LENGTH_LONG).show();
        }
//        cardAdapter2.notifyDataSetChanged();
    }
}
