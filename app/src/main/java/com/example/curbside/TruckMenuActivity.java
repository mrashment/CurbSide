package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TruckMenuActivity extends AppCompatActivity {

    private static final String TAG = "TruckMenuActivity";
    private Truck truck;
    private Button backToHomeButton, navigationButton;
    private TextView truckNameText;
    private TextView companyNameText;
    private TextView hoursTruckText;
    private DbConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        conn = DbConnection.getInstance();

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

                if(isPressed==false){ // favoriting

                    imageButton.setBackgroundResource(R.drawable.ic_nonfavorite);
                    isPressed=true;
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.OPERATION.Add);
                    thread.execute(conn.getUser().getId(),truck.getCompany().getId());
                    Log.d(TAG, "onClick: Favoriting with companyid = " + truck.getCompany().getId());

                }else if(isPressed==true){ // unfavoriting

                    imageButton.setBackgroundResource(R.drawable.ic_favorite);
                    isPressed=false;
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.OPERATION.Delete);
                    thread.execute(conn.getUser().getId(),truck.getCompany().getId());
                    Log.d(TAG, "onClick: Unfavoriting with companyid = " + truck.getCompany().getId());
                }
            }
        });
    }

    private ImageButton imageButton;

}