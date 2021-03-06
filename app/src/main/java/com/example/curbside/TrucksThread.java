package com.example.curbside;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TrucksThread extends AsyncTask<Double,Void, Boolean> {
    private static final String TAG = "TrucksThread";
    private Double mLatitude,mLongitude;
    private ArrayList<Truck> trucks;
    private Context context;

    public TrucksThread(Context context) {
        this.context = context;
        this.trucks = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            ((HomeActivityJava)context).displayNearbyTrucks(trucks);
        }
    }

    @Override
    protected Boolean doInBackground(Double... doubles) {
        mLatitude = doubles[0];
        mLongitude = doubles[1];
        String stringData = "latitude=" + mLatitude + "&longitude=" + mLongitude;
        byte[] postData = stringData.getBytes();
        StringBuilder sb;

        try {
            URL url = new URL(DbConnection.NEARBY_TRUCKS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
            outputPost.write(postData);
            outputPost.flush();
            outputPost.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("Server error")) {
                    return false;
                }
                Log.d(TAG, "doInBackground: " + line.trim());
                JSONObject nextTruck = new JSONObject(line.trim());
                Log.d(TAG, "doInBackground: created JSONObject");
                Truck truck = new Truck(nextTruck.getString("name"),new Company(nextTruck.getString("cname"),nextTruck.getInt("cid")));
                try {
                    truck.setHours(nextTruck.getString("open_time") + "-" + nextTruck.getString("close_time"));
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: no open or close time");
                }
                truck.setBio(nextTruck.getString("bio"));
                truck.setLat(nextTruck.getDouble("latitude"));
                truck.setLng(nextTruck.getDouble("longitude"));
                truck.setDistance(nextTruck.getDouble("distance"));
                trucks.add(truck);
                Log.d(TAG, "doInBackground: added Truck");
            }
            reader.close();

        } catch (MalformedURLException e) {
            System.err.println(TAG + "MalformedURLException : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(TAG + "IOException : " + e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
