package com.example.curbside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ContactDevelopers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_developers);
    }

    //Intent intent = new Intent(Intent.ACTION_SEND);intent.setType("text/plain"); // send email as plain text
    //intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"some@email.address"});
    //intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
    //intent.putExtra(Intent.EXTRA_TEXT, "mail body");
    //startActivity(Intent.createChooser(intent, ""));


}
