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
    private EditText editTruckHours, editTruckHours2, editTruckName, editTruckBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_interface);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        backToSeeTrucks = findViewById(R.id.backToSeeTrucks);
        applyTruckChange = findViewById(R.id.applyTruckChange);

        editTruckHours = findViewById(R.id.editTruckHours);
        editTruckHours2 = findViewById(R.id.editTruckHours2);
        editTruckName = findViewById(R.id.editTruckName);
        editTruckBio = findViewById(R.id.editTruckBio);

        editTruckName.setText(truck.getName());
        String hours[] = truck.getHours().split("-");
        String open = hours[0];
        String close = hours[1];
        editTruckHours.setText(open);
        editTruckHours2.setText(close);
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
