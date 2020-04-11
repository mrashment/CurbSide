package com.example.curbside;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
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

/**
 * Should only be used to retrieve user information on login or if you are sure they already
 * exist in the database. Otherwise it will create a new user and store them.
 */
class RetrieveThread extends AsyncTask<GoogleSignInAccount,Void,String> {
    private static final String TAG = "RetrieveThread";
    private byte[] postData;
    GoogleSignInAccount account;
    private DbConnection conn;

    public RetrieveThread() {

    }

    @Override
    protected void onPostExecute(String response) {
        JSONObject jsonUser;
        JSONObject jsonFavorites;
        conn = DbConnection.getInstance();

        switch(response.trim()) {
            case "Failed Connection":


            case "Invalid Email":

                String toPost = "email=" + account.getEmail() + "&name=" + account.getDisplayName() + "&rewards=0";
                AddThread thread = new AddThread();
                thread.execute(account);
                break;

            case "Server error":
                Log.d(TAG, "onPostExecute: Response not json");
                break;
            default:
                try {
                    String both = response.trim();
                    String[] separate = both.split("\n");
                    Log.d(TAG, "onPostExecute: " + both);

                    jsonUser = new JSONObject(separate[0].trim());
                    Log.d(TAG, "onPostExecute: " + jsonUser);
                    conn.setUser(jsonUser);
                    String[] items = separate[1].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                    conn.getUser().setFavIds(items);
                    Log.d(TAG, "onPostExecute: " + conn.printUserInfo());
                    Log.d(TAG, "onPostExecute: Company id: " + conn.getUser().getCompanyID());
                    Log.d(TAG, "onPostExecute: " + separate[1]);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected String doInBackground(GoogleSignInAccount... accounts) {
        account = accounts[0];
        String stringData = "email=" + account.getEmail();
        postData = stringData.getBytes();
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
                sb.append(line).append("\n");
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
