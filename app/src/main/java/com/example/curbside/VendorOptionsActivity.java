package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VendorOptionsActivity extends AppCompatActivity {

    private Button backToHomeButton, trucksButton, menusButton,retrieveQRButton;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_options);

        lat = getIntent().getDoubleExtra("lat",0);
        lng = getIntent().getDoubleExtra("lng",0);
        backToHomeButton = findViewById(R.id.backToHomeButton);
        trucksButton = findViewById(R.id.trucksButton);
        menusButton = findViewById(R.id.menusButton);
        retrieveQRButton = findViewById(R.id.retqr);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trucksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorOptionsActivity.this, SeeTrucksActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
            }
        });

        menusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorOptionsActivity.this, EditMenuInterface.class);
                startActivity(intent);
            }
        });

        retrieveQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorOptionsActivity.this, RetrieveQRActivity.class);
                startActivity(intent);
            }
        });


    }
}
