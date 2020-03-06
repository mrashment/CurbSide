package com.example.curbside;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FavoritesThread extends AsyncTask<Integer[],Void, ArrayList<Truck>> {
    private static final String TAG = "FavoritesThread";



    @Override
    protected ArrayList<Truck> doInBackground(Integer[]... integers) {
        Integer[] ids = integers[0];

        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                idBuilder.append("&");
            }
            idBuilder.append("ids[").append(i).append("]=").append(ids[i]);
        }
        byte[] postData = idBuilder.toString().getBytes();

        StringBuilder sb;

        String jsonString = "Failed Connection";
        try {
            URL url = new URL(DbConnection.USER_INFO);
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
                sb.append(line).append("\n");
            }
            reader.close();


            jsonString = sb.toString();



        } catch (MalformedURLException e) {
            System.err.println(TAG + "MalformedURLException : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(TAG + "IOException : " + e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doInBackground: jsonString = " + jsonString);



    }
}
