package com.example.curbside;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddItemThread extends AsyncTask<Integer, Void, Boolean> {
    private static final String TAG = "DropItemThread";

    private int id;
    private String name;
    private String description;
    private double price;
    private ArrayList<FoodItem> items;
    private String item_type;
    private int favorite;

    public AddItemThread( FoodItem item) {
        this.id = item.getId();
        this.name = item.getName().replace(" ","%20");
        this.description = item.getDescription().replace(" ","%20");
        this.price = item.getPrice();
        this.item_type = item.getItem_type();
        this.favorite = item.getFavorite();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {


        String stringData = "item_id=" + id +
                "&company_id=" + DbConnection.getInstance().getUser().getCompanyID() +
                "&item_name=" + name +
                "&item_description=" + description +
                "&item_price=" + price +
                "&item_type=" + item_type +
                "&item_favorite=" + favorite;

        byte[] postData = stringData.getBytes();

        try {
            // determine which script to call
            URL url = new URL(DbConnection.EDIT_ITEM);
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
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            if (!result.trim().equalsIgnoreCase("Success")) {
                return false;
            }

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
