package com.example.curbside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddMenuItemActivity extends AppCompatActivity {
    private static final String TAG = "AddMenuItemActivity";
    private Button backToEditMenu, finalizeItem;
    private TextView newItemName, newItemDescription, newItemPrice;
    private CheckBox favCheck;
    private RadioGroup typeGroup;
    private RadioButton entreeButton,sideButton,drinkButton;
    private FoodItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

        newItemName = findViewById(R.id.newItemName);
        newItemDescription = findViewById(R.id.newItemDescription);
        newItemPrice = findViewById(R.id.newItemPrice);
        favCheck = findViewById(R.id.favCheck);
        entreeButton = findViewById(R.id.typeEntree);
        sideButton = findViewById(R.id.typeSide);
        drinkButton = findViewById(R.id.typeDrink);
        typeGroup = findViewById(R.id.typeGroup);

        try {
            item = (FoodItem) getIntent().getSerializableExtra("com.example.curbside.item");
            newItemName.setText(item.getName());
            newItemDescription.setText(item.getDescription());
            newItemPrice.setText(Double.toString(item.getPrice()));
            if (item.getFavorite() == 1) {
                favCheck.setChecked(true);
            }
            String type = item.getItem_type();
            if (type.equalsIgnoreCase("entree")) {
                entreeButton.setChecked(true);
            } else if (type.equalsIgnoreCase("side")) {
                sideButton.setChecked(true);
            } else {
                drinkButton.setChecked(true);
            }
        } catch (Exception e) {
            // No item, probably coming from add button rather than editing item
            item = new FoodItem(-1,"null","null",0.0,"entree",0);
        }
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

                item.setName(newItemName.getText().toString());
                item.setDescription(newItemDescription.getText().toString());
                item.setPrice(Double.parseDouble(newItemPrice.getText().toString().trim()));
                if (favCheck.isChecked()) {
                    item.setFavorite(1);
                } else {
                    item.setFavorite(0);
                }
                item.setItem_type(((RadioButton)findViewById(typeGroup.getCheckedRadioButtonId())).getText().toString());

                AddItemThread thread = new AddItemThread(item);
                thread.execute();
                Log.d(TAG, "onCheckedChanged: item_id = " + item.getId());

                finish();
            }
        });
    }

}
