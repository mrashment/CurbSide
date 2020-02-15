package com.example.curbside;

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

class RetrieveThread extends AsyncTask<String,Void,String> {
    private static final String TAG = "RetrieveThread";

    @Override
    protected void onPostExecute(String response) {

        JSONObject json;
        DbConnection conn = DbConnection.getInstance();

        switch(response.trim()) {
            case "Failed Connection":


            case "Invalid Email":


            case "Server error":
                Log.d(TAG, "onPostExecute: Response not json");
                break;
            default:
                try {
                    json = new JSONObject(response);
                    conn.setUser(json);
                    Log.d(TAG, "onPostExecute: " + conn.getUserInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        byte[] postData = strings[0].getBytes();
        StringBuilder sb;
        Log.d(TAG, "retrieveUserInfo: before try block");

        String jsonString = "Failed Connection";
        try {
            URL url = new URL(DbConnection.USER_INFO);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "retrieveUserInfo: after connection opened");

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);


            Log.d(TAG, "retrieveUserInfo: after setRequestMethod");

            OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
            outputPost.write(postData);
            outputPost.flush();
            outputPost.close();

            Log.d(TAG, "retrieveUserInfo: before reader");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }
            reader.close();

            Log.d(TAG, "retrieveUserInfo: after reader, sb has " + sb.toString());

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
        return jsonString;
    }
}
