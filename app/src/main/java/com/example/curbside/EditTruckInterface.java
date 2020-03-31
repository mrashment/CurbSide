package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditTruckInterface extends AppCompatActivity {
    private Button backToEditTrucks, applyTruckChange;
    private Truck truck;
    private View editTruckName, editTruckHours;

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

        backToEditTrucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTruckInterface.this, EditTrucks.class);
                startActivity(intent);
            }
        });
    }

}
