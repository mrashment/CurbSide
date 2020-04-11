package com.example.curbside;

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

public class RewardsThread extends AsyncTask<Void,Void,Integer> {

    private static final String TAG = "RewardsThread";

    private DbConnection conn;

    public RewardsThread() {
        conn = DbConnection.getInstance();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        conn.getUser().setRewards(integer);
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
        String email = conn.getUser().getEmail();

        String stringData = "email=" + email;

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

            if (response.equalsIgnoreCase("Server Error")) {return 0;}
            else {
                intResponse = Integer.parseInt(response);
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
