package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditMenuInterface extends AppCompatActivity {
    private Button backToEditTrucks, applyMenuChange, addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu_interface);

        backToEditTrucks = findViewById(R.id.backToEditTrucks);
        applyMenuChange = findViewById(R.id.applyMenuChange);
        addItemButton = findViewById(R.id.addItemButton);

        backToEditTrucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMenuInterface.this, EditTrucks.class);
                startActivity(intent);
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMenuInterface.this, AddMenuItemActivity.class);
                startActivity(intent);
            }
        });

        applyMenuChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                }
        });
    }
}
