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

public class SeeItemsThread extends AsyncTask<Integer, Void, Boolean> {
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
            ((EditMenuInterface)context).displayItems(items);
        }
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        String toPost = "company_id=" + integers[0];
        byte[] postData = toPost.getBytes();

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
                FoodItem item = new FoodItem(nextItem.getInt("item_id")
                        ,nextItem.getString("name")
                        ,nextItem.getString("description")
                        ,nextItem.getDouble("price")
                        ,nextItem.getString("item_type")
                        ,nextItem.getInt("favorite"));
                items.add(item);
                Log.d(TAG, "doInBackground: added Item");
            }
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }

        return true;
    }


}
