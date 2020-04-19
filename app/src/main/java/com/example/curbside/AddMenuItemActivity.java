package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                finish();
            }
        });

        finalizeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
//                AddItemThread thread = new AddItemThread(DropItemThread.get(pos).getId());
//                thread.execute();
//                Log.d(TAG, "onCheckedChanged: item_id = " + items.getId());


                finish();
            }
        });
    }

}
