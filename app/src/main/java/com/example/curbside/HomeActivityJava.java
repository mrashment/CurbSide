package com.example.curbside;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class HomeActivityJava extends AppCompatActivity implements OnMapReadyCallback, PermissionListener {
    private static final String TAG = "HomeActivityJava";
    private static final int REQUEST_CHECK_SETTINGS = 43;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<Truck> trucks;
    private RecyclerView recyclerView;
    private HomePageCardAdapter cardAdapter;
    private Button locationButton,menuButton, vendorButton, favoritesButton;
    private SearchView searchBar;
    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        handler = new HomeHandler(this);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(RecyclerView.INVISIBLE);


        locationButton  = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityJava.this, Profile.class);
                startActivity(intent);
            }
        });



        vendorButton = findViewById(R.id.vendorButton);
        vendorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityJava.this, VendorOptionsActivity.class);
                startActivity(intent);
            }
        });
        favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityJava.this,FavoritesActivity.class);
                startActivity(intent);
            }
        });
        searchBar = findViewById(R.id.sv_location);
        searchBar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchView)v).setIconified(true);
                startActivity(new Intent(HomeActivityJava.this,SearchActivity.class));

            }
        });
   }


    private void findNearbyTrucks() {
        TrucksThread trucksThread = new TrucksThread(this);
        Log.d(TAG, "findNearbyTrucks: latitude = " + mLastLocation.getLatitude() + " longitude = " + mLastLocation.getLongitude() );
        trucksThread.execute(mLastLocation.getLatitude(),mLastLocation.getLongitude());

    }




    public void displayNearbyTrucks(ArrayList<Truck> fromThread) {
        trucks = fromThread;
        if (trucks != null) {
            recyclerView.setVisibility(RecyclerView.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            cardAdapter = new HomePageCardAdapter(trucks, googleMap, this);
            recyclerView.setAdapter(cardAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.HORIZONTAL));

        } else {
            Toast.makeText(this,"No nearby trucks",Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onMapReady(GoogleMap gMap) {
        if (gMap != null) {
            googleMap = gMap;
        } else return;

        if (isPermissionGiven()) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            getCurrentLocation();

        } else {
            givePermission();
        }

    }



    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval((long)(10*1000));
        locationRequest.setFastestInterval(2000L);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this).checkLocationSettings(locationSettingsRequest);
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//                try {
                    LocationSettingsResponse response = task.getResult();
                    if (response != null) {
                        response.getLocationSettingsStates();
                        getLastLocation();
                    } else throw new NullPointerException();
//                } catch(ApiException e) {
//                    if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) try {
//                        ResolvableApiException resolvable = new ResolvableApiException(new Status(e.getStatusCode()));
//                    } catch (ClassCastException c) {
//                        c.printStackTrace();
//                    }
//
//                    else if(e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {}
//                }
            }
        });
    }

    private Location mLastLocation;

    public Double getCurLat() {
        if (mLastLocation == null) {
            return null;
        }
        return mLastLocation.getLatitude();
    }

    public Double getCurLng() {
        if (mLastLocation == null) {
            return null;
        }
        return mLastLocation.getLongitude();
    }

    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            String address = "No known address";

                            Geocoder gcd = new Geocoder(HomeActivityJava.this,Locale.getDefault());
                            List<Address> addresses = new ArrayList<>();
                            try {
                                if (mLastLocation != null) {
                                    addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                                } else {throw new NullPointerException("Expression 'mLastLocation' must not be null");}
                                if (!addresses.isEmpty()) {
                                    address = addresses.get(0).getAddressLine(0);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                                    .zoom(17f)
                                    .build();
                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            findNearbyTrucks();
                            FavoritesThread favoritesThread = new FavoritesThread();
                            favoritesThread.execute();


                        } else {
                            Toast.makeText(HomeActivityJava.this, "No current location found", Toast.LENGTH_LONG).show();
                        }

                    }

                });
    }


    private boolean isPermissionGiven() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void givePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(this)
                .check();
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        getCurrentLocation();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        Toast.makeText(this, "Permission required for showing location", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        if (token != null) token.continuePermissionRequest();
        else throw new NullPointerException("Expression 'token' must not be null");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                getCurrentLocation();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void signOut() {

    }

    static class HomeHandler extends Handler {
        HomeActivityJava context;

        public HomeHandler(Context context) {
            this.context = ((HomeActivityJava)context);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Double lat = context.getCurLat();
            Double lng = context.getCurLng();
            int trucksNearby = 0;

            for (Truck t : DbConnection.getInstance().getUser().getFavTrucks()) {
                double tlat = t.getLat();
                double tlng = t.getLng();
                double distance = 3959 * Math.acos(Math.cos(Math.toRadians(lat)) *
                        Math.cos(Math.toRadians(tlat)) *
                        Math.cos(Math.toRadians(tlng) - Math.toRadians(lng)) +
                        Math.sin(Math.toRadians(lat)) *
                        Math.sin(Math.toRadians(tlat)));
                t.setDistance(distance);
                if (distance <= 2) {
                    trucksNearby++;
                }
            }

            Snackbar.make(context.findViewById(R.id.snackbarLayout),"You have " + trucksNearby + " favorites nearby!",Snackbar.LENGTH_LONG).show();
//            Toast.makeText(context, "User has " + DbConnection.getInstance().getUser().getFavTrucks().toString() + " favorites.",Toast.LENGTH_LONG).show();
        }
    }
}
