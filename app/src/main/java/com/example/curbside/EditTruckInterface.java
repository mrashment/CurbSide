package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class EditTruckInterface extends AppCompatActivity {
    private Button backToEditTrucks, applyTruckChange;
    private Truck truck;
    ArrayList<Truck> trucks;
    Context context;
    private View editTruckName, editTruckHours;
    private TextView companyNameText, hoursTruckText;

    public EditTruckInterface(ArrayList<Truck> trucks, Context context) {
        this.trucks = trucks;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_interface);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        backToEditTrucks = findViewById(R.id.backToEditTrucks);
        applyTruckChange = findViewById(R.id.applyTruckChange);
        editTruckHours = findViewById(R.id.editTruckHours);
        editTruckName = findViewById(R.id.editTruckName);
        companyNameText = findViewById(R.id.companyNameText);
        hoursTruckText = findViewById(R.id.hoursTruckText);

//        grabs the current truck info from the Db
        holder.companyNameText.setText(trucks.get(position).getName());
        holder.hoursTruckText.setText(trucks.get(position).getHours());

        backToEditTrucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTruckInterface.this, EditTrucks.class);
                startActivity(intent);
            }
        });

        applyTruckChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update truck database
            }
        });
    }
}
