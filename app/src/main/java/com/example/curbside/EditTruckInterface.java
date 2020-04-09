package com.example.curbside;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class EditTruckInterface extends AppCompatActivity {
    private Button backToSeeTrucks, applyTruckChange;
    private Truck truck;
    ArrayList<Truck> trucks;
    Context context;
    private TextView companyNameText, hoursTruckText;
    private EditText editTruckHours, editTruckHours2, editTruckName, editTruckBio;

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

        backToSeeTrucks = findViewById(R.id.backToSeeTrucks);
        applyTruckChange = findViewById(R.id.applyTruckChange);

        companyNameText = findViewById(R.id.companyNameText);

        editTruckHours = findViewById(R.id.editTruckHours);
        editTruckHours2 = findViewById(R.id.editTruckHours2);
        editTruckName = findViewById(R.id.editTruckName);
        editTruckBio = findViewById(R.id.editTruckBio);


//        grabs the current truck info from the Db
        companyNameText.setText(truck.get(position).getName());
        hoursTruckText.setText(truck.get(position).getHours());

        editTruckName.setText(truck.getName());
        editTruckHours.setText(truck.getHourOpen());
        editTruckHours2.setText(truck.getHourClose());
        editTruckBio.setText(truck.getBio());

        backToSeeTrucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTruckInterface.this, EditTrucks.class);
                startActivity(intent);
            }
        });

        applyTruckChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
