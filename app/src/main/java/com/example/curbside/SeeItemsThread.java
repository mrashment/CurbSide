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

public class SeeItemsThread extends AsyncTask<Double, Void, Boolean> {
    private static final String TAG = "SeeItemsThread";
    private ArrayList<FoodItem> items;
    private Context context;

    public SeeItemsThread(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            ((SeeTrucksActivity)context).displayItems(items);
        }
    }

    @Override
    protected Boolean doInBackground(Double... doubles) {
        StringBuilder sb;

        try {
            URL url = new URL(DbConnection.SEE_ITEMS);
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
                JSONObject nextItem = new JSONObject(line.trim());
                Log.d(TAG, "doInBackground: created JSONObject");
                FoodItem item = new FoodItem(nextItem.getString("name"));

                item.setName(nextItem.getString("name"));
                item.setPrice(nextItem.getDouble("price"));
                item.setDescription(nextItem.getString("description"));
                item.setItem_type(nextItem.getString("item_type"));
                items.add(item);
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
