package com.example.fakebookone.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fakebookone.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class LogoutActivity extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);



    }

}
