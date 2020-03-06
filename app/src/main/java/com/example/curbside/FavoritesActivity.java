package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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


    }
}
