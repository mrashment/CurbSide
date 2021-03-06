package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    Button backToHomeButton;
    RecyclerView recyclerView;
    ArrayList<Truck> favorites;
    DbConnection conn;

    @Override
    protected void onResume() {
        super.onResume();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        favorites = conn.getUser().getFavTrucks();
                        sort();
                        recyclerView.setAdapter(new FavoritesAdapter(favorites,FavoritesActivity.this));
                    }
                });
            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        conn = DbConnection.getInstance();
        while (conn.userIsNull()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        favorites = new ArrayList<>();

        favorites = conn.getUser().getFavTrucks();
        sort();

        backToHomeButton = findViewById(R.id.backToHomeButton);
        recyclerView = findViewById(R.id.favRecyclerView);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new FavoritesAdapter(favorites,this));


    }

    public void sort() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            favorites.sort(new TruckDistanceAsc());
        }
    }

}
