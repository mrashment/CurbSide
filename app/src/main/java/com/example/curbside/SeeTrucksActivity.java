package com.example.curbside;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SeeTrucksActivity extends AppCompatActivity {

    static Handler handler;
    private ArrayList<Truck> trucks;
    private RecyclerView recyclerView;
    private HomePageCardAdapter cardAdapter;

    public SeeTrucksActivity(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_trucks);

//        handler = new SeeTrucksActivity(<ArrayList>trucks).HomeHandler(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(RecyclerView.INVISIBLE);
    }

    private void findCompanyTrucks() {
        TrucksThreadCompany trucksThread = new TrucksThreadCompany(this);
        Log.d(TAG, "findCompanyTrucks: companyID = " );
        trucksThread.execute();
    }

    public void displayCompanyTrucks(ArrayList<Truck> fromThread) {
        trucks = fromThread;
        if (trucks != null) {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//            cardAdapter = new SeeTrucksCardAdapter(trucks);

            recyclerView.setAdapter(cardAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));

        } else {
            Toast.makeText(this,"No nearby trucks",Toast.LENGTH_LONG).show();
        }
    }
}
