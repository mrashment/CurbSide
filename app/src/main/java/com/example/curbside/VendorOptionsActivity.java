package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VendorOptionsActivity extends AppCompatActivity {

    Button backToHomeButton, trucksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = DbConnection.getInstance().getUser();

        // TODO create layout for non-vendors

//        if (user.getPermissions() == 1) {
////            setContentView(R.layout.activity_vendor_invite);
////        }
//        else {
            setContentView(R.layout.activity_vendor_options);
//        }

        backToHomeButton = findViewById(R.id.backToHomeButton);
        trucksButton = findViewById(R.id.trucksButton);

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
                startActivity(intent);
            }
        });

    }
}
