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

public class BroadcastThread extends AsyncTask<Double,Void,Boolean> {
    private static final String TAG = "BroadcastThread";

    enum Operation {START,STOP}
    private Operation op;
    private int truckId;

    public BroadcastThread(Operation op, int truckId) {
        this.op = op;
        this.truckId = truckId;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(Double... doubles) {
        String stringData = "truckid=" + truckId + "&lat=";
        stringData += op == Operation.START ? doubles[0] : "";
        stringData += "&lng=";
        stringData += op == Operation.START ? doubles[1] : "";


        byte[] postData = stringData.getBytes();

        try {
            // determine which script to call
            URL url = new URL(DbConnection.BROADCAST);
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
