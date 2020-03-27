package com.example.curbside;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Execute with user id first, then company id
 */
public class UpdateFavoriteThread extends AsyncTask<Integer, Void, Boolean> {

    private static final String TAG = "UpdateFavoriteThread";
    enum OPERATION {Add,Delete}

    private OPERATION op;

    public UpdateFavoriteThread(OPERATION operation) {
        this.op = operation;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        String stringData = "userid=" + integers[0] + "&companyid=" + integers[1];

        byte[] postData = stringData.getBytes();

        try {
            // determine which script to call
            URL url = op == OPERATION.Add ? new URL(DbConnection.ADD_FAVORITE) : new URL(DbConnection.DELETE_FAVORITE);
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
