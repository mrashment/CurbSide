package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class TruckMenuActivity extends AppCompatActivity {

    private Truck truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        imageButton = findViewById(R.id.truckFavoriteButton);
        Toast.makeText(this,"The current truck is " + truck.getName(),Toast.LENGTH_LONG).show();

        imageButton.setOnClickListener(new View.OnClickListener(){
            boolean isPressed = false;
            public void onClick(View v){

                if(isPressed==false){

                    imageButton.setBackgroundResource(R.drawable.truck_favorite_pressed);
                    isPressed=true;

                }else if(isPressed==true){

                    imageButton.setBackgroundResource(R.drawable.truck_favorite_unpressed);
                    isPressed=false;

                }
            }
        });
    }

    private ImageButton imageButton;



}
