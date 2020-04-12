package com.example.curbside;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TrucksEditTruckThread extends AsyncTask<Truck,Void, Boolean> {

    private static final String TAG = "TrucksEditTruckThread";
    private ArrayList<Truck> trucks;
    private byte[] postData;


    public TrucksEditTruckThread() {
        this.trucks = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
//            ((SeeTrucksActivity)context).applyChanges(trucks);
        }
    }

    @Override
    protected Boolean doInBackground(Truck... trucks) {
        Truck truck = trucks[0];

        Truck editted = trucks[1];

        String original = truck.getName().replace(" ","%20");
        String name = editted.getName().replace(" ","%20");
        String bio = editted.getBio().replace(" ","%20");

        String toPost = "original=" + original + "&newName=" + name + "&newHours=" + editted.getHours() + "&newBio=" + bio;
        postData = toPost.getBytes();

        Log.d(TAG, "doInBackground: " + toPost);

        try {
            URL url = new URL(DbConnection.EDIT_TRUCK);
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
                if (line.trim().equals("Server error") || line.trim().equalsIgnoreCase("Failure")) {
                    Log.d(TAG, "doInBackground: failed");
                    return false;
                }

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
