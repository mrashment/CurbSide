package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<FoodItem> items;
    private RecyclerView recyclerView3;
    private SeeItemsTruckAdapter SeeItemsTruckAdapter;
    private boolean isPressed = false;
    private View ConstraintLayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_menu);
        Intent intent = getIntent();
        truck = (Truck)intent.getSerializableExtra("com.example.curbside.truck");

        new SeeItemsTruckThread(this).execute(truck.getCompany().getId());

        conn = DbConnection.getInstance();
        favs = conn.getUser().getFavIds();

        truckNameText = findViewById(R.id.truckNameText);
        companyNameText = findViewById(R.id.companyNameText);
        hoursTruckText = findViewById(R.id.hoursTruckText);
        recyclerView3 = findViewById(R.id.recyclerView3);

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

        ConstraintLayout2 = findViewById(R.id.constraintLayout2);
        ConstraintLayout2.bringToFront();

        backToHomeButton = findViewById(R.id.backToHomeButton);

        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<Integer> favList = Arrays.asList(favs);
        if (favList.contains(truck.getCompany().getId())) {
            imageButton.setBackgroundResource(R.drawable.ic_favorite);
            isPressed = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(isPressed==false){ // favoriting

                    imageButton.setBackgroundResource(R.drawable.ic_favorite);
                    isPressed=true;
                    Integer[] curFavs = conn.getUser().getFavIds();
                    Integer[] newFavs = Arrays.copyOf(curFavs,curFavs.length + 1);
                    newFavs[newFavs.length-1] = truck.getCompany().getId();
                    conn.getUser().setFavIds(newFavs);
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.Operation.ADD);
                    thread.execute(conn.getUser().getId(),truck.getCompany().getId());
                    new FavoritesThread().execute();
                    Log.d(TAG, "onClick: Favoriting with companyid = " + truck.getCompany().getId());

                }else if(isPressed==true){ // unfavoriting

                    imageButton.setBackgroundResource(R.drawable.ic_nonfavorite);
                    isPressed=false;
                    UpdateFavoriteThread thread = new UpdateFavoriteThread(UpdateFavoriteThread.Operation.DELETE);
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

    public void displayItems(ArrayList<FoodItem> fromThread) {
        items = fromThread;
        if (items != null) {
            recyclerView3.setVisibility(RecyclerView.VISIBLE);
            recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            SeeItemsTruckAdapter = new SeeItemsTruckAdapter(items, this);
            recyclerView3.setAdapter(SeeItemsTruckAdapter);
            recyclerView3.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));

        } else {
            Toast.makeText(this,"No Company Items",Toast.LENGTH_LONG).show();
        }
//        cardAdapter2.notifyDataSetChanged();
    }

    private ImageButton imageButton;

}