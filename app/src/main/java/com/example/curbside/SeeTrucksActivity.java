package com.example.curbside;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SeeTrucksActivity extends Context AppCompatActivity implements PermissionListener {

    static Handler handler;
    private ArrayList<Truck> trucks;
    private RecyclerView recyclerView;
    private HomePageCardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_trucks);

        handler = new SeeTrucksActivity().HomeHandler(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(RecyclerView.INVISIBLE);
    }

    private void findCompanyTrucks() {
        TrucksThread trucksThread = new TrucksThread(this);
        Log.d(TAG, "findCompanyTrucks: companyID = " );
        trucksThread.execute();
    }

    public void displayCompanyTrucks(ArrayList<Truck> fromThread) {
        trucks = fromThread;
        if (trucks != null) {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            cardAdapter = new HomePageCardAdapter(trucks,this);
            recyclerView.setAdapter(cardAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));

        } else {
            Toast.makeText(this,"No nearby trucks",Toast.LENGTH_LONG).show();
        }
    }
}
