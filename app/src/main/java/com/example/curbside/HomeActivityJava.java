package com.example.curbside;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    private static final int REQUEST_CHECK_SETTINGS = 43;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        if (gMap != null) {
            googleMap = gMap;
        } else return;

        if (isPermissionGiven()) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
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

    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location mLastLocation = task.getResult();

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

                            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(HomeActivityJava.this.getResources(), R.drawable.ic_pickup));
                            if (mLastLocation != null) {
                                googleMap.addMarker(
                                        new MarkerOptions()
                                                .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                                                .title("Current Location")
                                                .snippet(address)
                                                .icon(icon));
                            } else {throw new NullPointerException("Expression 'mLastLocation' must not be null");}

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                                    .zoom(17f)
                                    .build();
                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}
