package com.example.curbside;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Date;

public class RewardsThread extends AsyncTask<Void,Void,Integer> {

    private static final String TAG = "RewardsThread";

    private DbConnection conn;
    private Context context;

    public RewardsThread(Context context) {
        this.context = context;
        conn = DbConnection.getInstance();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer != 0) {
            conn.getUser().setRewards(integer);
            ((ScannedBarcodeActivity)context).updateCounter();

        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        while (conn.userIsNull()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long time = new Date().getTime();
        String stringData = "user_id=" + conn.getUser().getId() + "&time=" + time;

        byte[] postData = stringData.getBytes();

        int intResponse = 0;

        try {
            URL url = new URL(DbConnection.ADD_REWARDS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
            outputPost.write(postData);
            outputPost.flush();
            outputPost.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = "";
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();

            if (response.trim().equalsIgnoreCase("Server Error")) {return 0;}
            else {
                try {
                    Log.d(TAG, "doInBackground: " + response);
                    intResponse = Integer.parseInt(response.trim());
                } catch (Exception e) {
                    return 0;
                }
            }

        } catch (MalformedURLException e) {
            System.err.println(TAG + "MalformedURLException : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(TAG + "IOException : " + e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
        }


        return intResponse;
    }
}
