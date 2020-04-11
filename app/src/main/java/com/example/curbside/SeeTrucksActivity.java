package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SeeTrucksActivity extends AppCompatActivity {
    private static final String TAG = "SeeTrucksActivity";
    static Handler handler;
    private ArrayList<Truck> trucks;
    private RecyclerView recyclerView1;
    private SeeTrucksActivity cardAdapter1;
    private Button backToVendorOptions, newTruckButton;

    public SeeTrucksActivity(ArrayList<Truck> trucks) {
        this.trucks = trucks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_trucks);

        backToVendorOptions = findViewById(R.id.backToVendorOptions);
        newTruckButton = findViewById(R.id.newTruckButton);

        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setVisibility(RecyclerView.INVISIBLE);

        backToVendorOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeTrucksActivity.this, VendorOptionsActivity.class);
                startActivity(intent);
            }
        });

        newTruckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeTrucksActivity.this, EditTruckInterface.class);
                startActivity(intent);
            }
        });



    }

    public void displayNearbyTrucks(ArrayList<Truck> fromThread) {
        trucks = fromThread;
        if (trucks != null) {
            recyclerView1.setVisibility(RecyclerView.VISIBLE);
            recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            cardAdapter1 = new SeeTrucksCardAdapter(trucks, this);
            recyclerView1.setAdapter(cardAdapter1);
            recyclerView1.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));

        } else {
            Toast.makeText(this,"No Company trucks",Toast.LENGTH_LONG).show();
        }
    }

}
