package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchBar;
    private Button backButton;
    private RecyclerView recyclerView;
    private ArrayList<Truck> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = findViewById(R.id.backButton);
        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.searchRecyclerView);


        results = new ArrayList<>();
        results = searchFor();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        FavoritesAdapter adapter = new FavoritesAdapter(results);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        searchBar.setOnQueryTextListener();

    }

    private ArrayList<Truck> searchFor() {
        ArrayList<Truck> searchResult = new ArrayList<>();


        return searchResult;
    }

}
