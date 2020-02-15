package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int RC_SIGN_IN = 0;
    Button signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private String clientID = "253173760480-le3ljf8ln8oc8osns4f1d7g19ambr119.apps.googleusercontent.com";
    DbConnection conn = DbConnection.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.btn_google_signin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String email = account.getEmail();

            retrieveUserInfo(account.getEmail());
            if (conn.userIsNull()) {
                addUser(account.getEmail(),account.getDisplayName());
            }


            startActivity(new Intent(MainActivity.this,HomeActivity.class));

        } catch (ApiException e) {
            Log.w("Google Sign In Error","signInResult:failed code =" + e.getStatusCode());
            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
        }
    }

    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.w(TAG, "onStart: account found" );
            retrieveUserInfo(account.getEmail());
            if (conn.userIsNull()) {
                addUser(account.getEmail(),account.getDisplayName());
            }

            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        super.onStart();
    }

    /**
     * Creates a new {@link User} object in {@link DbConnection} from info retrieved from the database
     *
     * @param email
     */
    private void retrieveUserInfo(String email) {
        String toPost = "email=" + email;
        RetrieveThread thread = new RetrieveThread();
        thread.execute(toPost);
    }
    private void addUser(String email, String name) {
        String toPost = "email=" + email + "&name=" + name + "&rewards=0";
        AddThread thread = new AddThread();
        thread.execute(toPost);
    }
}














