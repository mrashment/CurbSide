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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TruckMenuActivity extends AppCompatActivity {

    private static final String TAG = "TruckMenuActivity";
    private Truck truck;
    private Button backToHomeButton, navigationButton;
    private TextView truckNameText;
    private TextView companyNameText;
    private TextView hoursTruckText;
    private DbConnection conn;
    private Integer[] favs;
    private boolean isPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        conn = DbConnection.getInstance();
        favs = conn.getUser().getFavIds();

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

        List<Integer> favList = Arrays.asList(favs);
        if (favList.contains(truck.getCompany().getId())) {
            imageButton.setBackgroundResource(R.drawable.truck_favorite_pressed);
            isPressed = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(isPressed==false){ // favoriting

                    imageButton.setBackgroundResource(R.drawable.ic_nonfavorite);
                    isPressed=true;
                    Integer[] curFavs = conn.getUser().getFavIds();
                    Integer[] newFavs = Arrays.copyOf(curFavs,curFavs.length + 1);
                    newFavs[newFavs.length-1] = truck.getCompany().getId();
                    conn.getUser().setFavIds(newFavs);
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.OPERATION.Add);
                    thread.execute(conn.getUser().getId(),truck.getCompany().getId());
                    new FavoritesThread().execute();
                    Log.d(TAG, "onClick: Favoriting with companyid = " + truck.getCompany().getId());

                }else if(isPressed==true){ // unfavoriting

                    imageButton.setBackgroundResource(R.drawable.ic_favorite);
                    isPressed=false;
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.OPERATION.Delete);
                    thread.execute(conn.getUser().getId(),truck.getCompany().getId());
                    Integer[] curFavs = conn.getUser().getFavIds();
                    Integer[] newFavs = new Integer[curFavs.length - 1];
                    int place = 0;
                    for (Integer i : curFavs) {
                        if (i != truck.getCompany().getId()) {
                            newFavs[place++] = i;
                        }
                    }
                    conn.getUser().setFavIds(newFavs);
                    new FavoritesThread().execute();
                    Log.d(TAG, "onClick: Unfavoriting with companyid = " + truck.getCompany().getId());
                }
            }
        });
    }

    private ImageButton imageButton;

}