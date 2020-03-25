package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditTrucks extends AppCompatActivity{
    private Button editTruck, editMenus, backToOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backToOptions = findViewById(R.id.backToOptions);
        editMenus = findViewById(R.id.editMenus);
        editTruck = findViewById(R.id.editTruck);

        backToOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTrucks.this, VendorOptionsActivity.class);
                startActivity(intent);
            }
        });

        editMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTrucks.this, EditMenus.class);
                startActivity(intent);
            }
        });

        editTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTrucks.this, EditTruck.class);
                startActivity(intent);
            }
        });
    }
}