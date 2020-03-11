package com.example.curbside;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
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

public class FavoritesThread extends AsyncTask<Integer[],Void, Boolean> {
    private static final String TAG = "FavoritesThread";
    private ArrayList<Truck> trucks;
    DbConnection conn;

    public FavoritesThread() {
        trucks = new ArrayList<>();

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        conn = DbConnection.getInstance();
        conn.getUser().setFavTrucks(trucks);
        HomeActivityJava.handler.sendEmptyMessage(0);

    }

    @Override
    protected Boolean doInBackground(Integer[]... integers) {
        Integer[] ids = integers[0];
        JSONArray json = new JSONArray();
        for (Integer i : ids) {
            json.put(i);
        }
        String stringData = "ids=" + json.toString();

        byte[] postData = stringData.getBytes();

        StringBuilder sb;

        String jsonString = "Failed Connection";
        try {
            URL url = new URL(DbConnection.FAVORITE_TRUCKS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
            outputPost.write(postData);
            outputPost.flush();
            outputPost.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("Server error")) {
                    return false;
                }
                Log.d(TAG, "doInBackground: " + line.trim());
                JSONObject nextTruck = new JSONObject(line.trim());
                Log.d(TAG, "doInBackground: created JSONObject");
                Truck truck = new Truck(nextTruck.getString("name"),new Company(nextTruck.getString("cname")));
                try {
                    truck.setHours(nextTruck.getString("open_time") + "-" + nextTruck.getString("close_time"));
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: no open or close time");
                }
                truck.setBio(nextTruck.getString("bio"));
                try {
                    truck.setLat(nextTruck.getDouble("latitude"));
                    truck.setLng(nextTruck.getDouble("longitude"));
                    double tlat = truck.getLat();
                    double tlng = truck.getLng();
                    double distance = 3959 * Math.acos(Math.cos(Math.toRadians(lat)) *
                            Math.cos(Math.toRadians(tlat)) *
                            Math.cos(Math.toRadians(tlng) - Math.toRadians(lng)) +
                            Math.sin(Math.toRadians(lat)) *
                                    Math.sin(Math.toRadians(tlat)));
                } catch (Exception e) {}
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

        Log.d(TAG, "doInBackground: jsonString = " + jsonString);


        return true;
    }
}
