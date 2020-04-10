package com.example.curbside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

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
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchBar;
    private Button backButton;
    private RecyclerView recyclerView;
    private ArrayList<Truck> results;
    private SearchAdapter adapter;
//    private static Handler searchResultHandler;
    private DbConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        searchResultHandler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                adapter.notifyDataSetChanged();
//            }
//        };

        conn = DbConnection.getInstance();

        backButton = findViewById(R.id.backButton);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setActivated(true);
        recyclerView = findViewById(R.id.searchRecyclerView);


        results = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new SearchAdapter(results);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchThread thread = new SearchThread();
                thread.execute(newText);
                return false;
            }
        });

    }

    private ArrayList<Truck> searchFor(String constraint) {
        ArrayList<Truck> searchResult = new ArrayList<>();


        return searchResult;
    }

    class SearchThread extends AsyncTask<String, Void, ArrayList<Truck>> {
        private static final String TAG = "SearchThread";
        ArrayList<Truck> newResults;

        @Override
        protected void onPostExecute(ArrayList<Truck> trucks) {
            if (trucks == null || trucks.size() == 0) {

            } else {
                adapter.updateResults(trucks);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected ArrayList<Truck> doInBackground(String... strings) {
            while (conn.userIsNull()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String constraint = strings[0];

            String stringData = "constraint=" + constraint;

            byte[] postData = stringData.getBytes();

            StringBuilder sb;

            try {
                URL url = new URL(DbConnection.SEARCH_TRUCKS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());
                outputPost.write(postData);
                outputPost.flush();
                outputPost.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                newResults = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals("Server error")) {
                        return null;
                    }
                    JSONObject nextTruck = new JSONObject(line.trim());
                    Truck truck = new Truck(nextTruck.getString("name"),new Company(nextTruck.getString("cname"),nextTruck.getInt("cid")));
                    try {
                        truck.setHours(nextTruck.getString("open_time") + "-" + nextTruck.getString("close_time"));
                    } catch (Exception e) {
                        Log.d(TAG, "doInBackground: no open or close time");
                    }
                    truck.setBio(nextTruck.getString("bio"));
                    try {
                        truck.setLat(nextTruck.getDouble("latitude"));
                        truck.setLng(nextTruck.getDouble("longitude"));
                    } catch (Exception e) {}
                    newResults.add(truck);
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


            return newResults;
        }
    }
}
