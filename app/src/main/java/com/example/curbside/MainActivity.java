package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int RC_SIGN_IN = 0;
    Button signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private String clientID = "253173760480-le3ljf8ln8oc8osns4f1d7g19ambr119.apps.googleusercontent.com";
    private DbConnection conn = DbConnection.getInstance();


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

            retrieveUserInfo(account);

            startActivity(new Intent(MainActivity.this,HomeActivityJava.class));

        } catch (ApiException e) {
            Log.w("Google Sign In Error","signInResult:failed code =" + e.getMessage());
            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
        }
    }

    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.w(TAG, "onStart: account found" );
            retrieveUserInfo(account);
            Log.d(TAG, "onStart: isNull = " + conn.userIsNull());

            startActivity(new Intent(MainActivity.this,HomeActivityJava.class));
        }
        super.onStart();
    }

    /**
     * Creates a new {@link User} object in {@link DbConnection} from info retrieved from the database
     *
     * @param account
     */
    private void retrieveUserInfo(GoogleSignInAccount account) {
        RetrieveThread thread = new RetrieveThread();
        thread.execute(account);
    }
}














