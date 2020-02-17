package com.example.curbside;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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

class AddThread extends AsyncTask<GoogleSignInAccount,Void,String> {
    GoogleSignInAccount account;
    private static final String TAG = "AddThread";
    private String prebytes;
    private byte[] postData;

    @Override
    protected void onPostExecute(String response) {

        JSONObject json;
        DbConnection conn = DbConnection.getInstance();

        switch(response.trim()) {
            case "Failed Connection":
            case "Failure":
            case "Email in use":
                Log.d(TAG, "onPostExecute: Tried to add new user, but email was in use");
                break;
            default:
                try {
                    json = new JSONObject(response);
                    conn.setUser(json);
                    Log.d(TAG, "onPostExecute: " + conn.printUserInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected String doInBackground(GoogleSignInAccount... accounts) {
        GoogleSignInAccount account = accounts[0];
        prebytes = "name=" + account.getDisplayName() + "&email=" + account.getEmail() + "&rewards=0";
        postData = prebytes.getBytes();
        StringBuilder sb;
        Log.d(TAG, "addUser: before try block");

        String result = "Failed Connection";
        try {
            URL url = new URL(DbConnection.USER_ADD);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "addUser: after connection opened");

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);


            Log.d(TAG, "addUser: after setRequestMethod");

            OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
            outputPost.write(postData);
            outputPost.flush();
            outputPost.close();

            Log.d(TAG, "addUser: before reader");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }
            reader.close();

            Log.d(TAG, "addUser: after reader, sb has " + sb.toString());

            result = sb.toString();



        } catch (MalformedURLException e) {
            System.err.println(TAG + "MalformedURLException : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(TAG + "IOException : " + e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doInBackground: jsonString = " + result);
        return result;
    }
}

