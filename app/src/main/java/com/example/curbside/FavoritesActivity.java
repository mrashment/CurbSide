package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    Button backToHomeButton;
    RecyclerView recyclerView;
    ArrayList<Truck> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites = new ArrayList<>();
        favorites = DbConnection.getInstance().getUser().getFavTrucks();

        backToHomeButton = findViewById(R.id.backToHomeButton);
        recyclerView = findViewById(R.id.favRecyclerView);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this,HomeActivityJava.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new FavoritesAdapter(favorites));


    }
}
