package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddMenuItemActivity extends AppCompatActivity {
    private Button backToEditMenu, finalizeItem;
    private View editItemName, editItemPrice, editItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item;

        backToEditMenu = findViewById(R.id.backToEditMenuInterface);
        finalizeItem = findViewById(R.id.finalizeItem);

        backToEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMenuItemActivity.this, EditMenuInterface.class);
                startActivity(intent);
            }
        });

        finalizeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                post to database (add row to table)

                Intent intent = new Intent(AddMenuItemActivity.this, EditMenuInterface.class);
                startActivity(intent);
            }
        });
    }

}
