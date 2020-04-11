package com.example.curbside;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class TrucksThreadCompany extends AsyncTask<Integer,Void, Boolean> {

    private static final String TAG = "TrucksThreadCompany";
    private ArrayList<Truck> trucks;
    private Context context;
    private byte[] postData;

    public TrucksThreadCompany(Context context) {
        this.context = context;
        this.trucks = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            ((SeeTrucksActivity)context).displayCompanyTrucks(trucks);
        }
    }

    @Override
    protected Boolean doInBackground(Integer... values) {
        StringBuilder sb;

        String postString = "company_id=" + values[0];
        postData = postString.getBytes();

        try {
            URL url = new URL(DbConnection.COMPANY_TRUCKS);
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
                Truck truck = new Truck(nextTruck.getString("name"),new Company(nextTruck.getString("cname")));
                try {
                    truck.setHours(nextTruck.getString("open_time") + "-" + nextTruck.getString("close_time"));
                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: no open or close time");
                }
                truck.setBio(nextTruck.getString("bio"));
                truck.setLat(nextTruck.getDouble("latitude"));
                truck.setLng(nextTruck.getDouble("longitude"));
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

