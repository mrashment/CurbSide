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
import android.widget.Toast;

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
        setContentView(R.layout.activity_edit_truck_interface);
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
                finish();
            }
        });

        applyTruckChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desiredName = editTruckName.getText().toString();
                String desiredOpen = editTruckHours.getText().toString();
                String desiredClose = editTruckHours2.getText().toString();
                String desiredBio = editTruckBio.getText().toString();
                Truck editted = new Truck(desiredName,truck.getCompany());
                editted.setHours(desiredOpen + "-" + desiredClose);
                editted.setBio(desiredBio);

                TrucksEditTruckThread thread = new TrucksEditTruckThread();
                thread.execute(truck,editted);

                Toast.makeText(EditTruckInterface.this,desiredName + " " + desiredBio,Toast.LENGTH_LONG).show();

            }
        });
    }
}
