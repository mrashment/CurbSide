package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TruckMenuActivity extends AppCompatActivity {

    private Truck truck;
    private Button backToHomeButton, navigationButton;
    private TextView truckNameText;
    private TextView companyNameText;
    private TextView hoursTruckText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        truckNameText = findViewById(R.id.truckNameText);
        companyNameText = findViewById(R.id.companyNameText);
        hoursTruckText = findViewById(R.id.hoursTruckText);

        truckNameText.setText(truck.getName());
        companyNameText.setText(truck.getCompany().getName());
        hoursTruckText.setText(truck.getHours());

        imageButton = findViewById(R.id.truckFavoriteButton);

        navigationButton = findViewById(R.id.navigationButton);

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + truck.getLat() + "," + truck.getLng() + "&origin&dir_action=navigate";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        backToHomeButton = findViewById(R.id.backToHomeButton);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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