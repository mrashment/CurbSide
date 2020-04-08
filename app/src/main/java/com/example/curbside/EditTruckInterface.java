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
    private Button backToEditTrucks, applyTruckChange;
    private Truck truck;
    ArrayList<Truck> trucks;
    Context context;
    private TextView companyNameText, hoursTruckText;
    private EditText editTruckHours, editTruckHours2, editTruckName;

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

        companyNameText = findViewById(R.id.companyNameText);
        hoursTruckText = findViewById(R.id.hoursTruckText);

        editTruckHours = findViewById(R.id.editTruckHours);
        editTruckHours2 = findViewById(R.id.editTruckHours2);
        editTruckName = findViewById(R.id.editTruckName);



//        grabs the current truck info from the Db
        companyNameText.setText(trucks.get(position).getName());
        hoursTruckText.setText(trucks.get(position).getHours());

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
                EditText editTruckName = (EditText)findViewById(R.id.editTruckName);
                String  stringEditTruckName = editTruckName.getText().toString().trim();

                EditText editTruckHours = (EditText)findViewById(R.id.editTruckHours);
                String  stringEditTruckHours = editTruckHours.getText().toString().trim();

                EditText editTruckHours2 = (EditText)findViewById(R.id.editTruckHours2);
                String  stringEditTruckHours2 = editTruckHours2.getText().toString().trim();

//                UPDATE Trucks
//                SET name = stringEditTruckName, open_time = stringEditTruckHours, close_time = stringEditTruckHours2
//                WHERE truck_id = ;
            }
        });
    }
}
