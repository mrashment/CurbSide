package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddMenuItemActivity extends AppCompatActivity {
    private Button backToEditMenu, finalizeItem;
    private TextView newItemName, newItemDescription, newItemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

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
                EditText newItemName = (EditText)findViewById(R.id.newItemName);
                String  stringNewItemName = newItemName.getText().toString().trim();

                EditText newItemDescription = (EditText)findViewById(R.id.newItemDescription);
                String  stringNewItemDescription = newItemDescription.getText().toString().trim();

                EditText editTruckName = (EditText)findViewById(R.id.newItemPrice);
                String  stringNewItemPrice = newItemPrice.getText().toString().trim();


//              send php code
//
//                Insert Into items ('name','description','price','favorite')
//                Values (stringNewItemName, stringNewItemDescription, stringNewItemPrice, );

//                async task, seperate thread to send tasks on


                Intent intent = new Intent(AddMenuItemActivity.this, EditMenuInterface.class);
                startActivity(intent);
            }
        });
    }

}
