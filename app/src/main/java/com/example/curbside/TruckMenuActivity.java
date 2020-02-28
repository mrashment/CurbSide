package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TruckMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);

        imageButton = findViewById(R.id.truckFavoriteButton);

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
