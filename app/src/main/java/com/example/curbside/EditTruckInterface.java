package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditTruckInterface extends AppCompatActivity {
    private Button backToEditTrucks, applyTruckChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_interface);

        backToEditTrucks = findViewById(R.id.backToEditTrucks);
        applyTruckChange = findViewById(R.id.applyTruckChange);

        backToEditTrucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTruckInterface.this, EditTrucks.class);
                startActivity(intent);
            }
        });
    }
}
