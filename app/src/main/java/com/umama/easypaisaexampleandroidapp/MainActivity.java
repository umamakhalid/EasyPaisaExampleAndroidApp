package com.umama.easypaisaexampleandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button restAPI, androidSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restAPI = findViewById(R.id.restAPI);
        androidSDK = findViewById(R.id.androidSDK);

        restAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EasyPaisaRestAPIs.class));
            }
        });

        androidSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EasyPaisaAndroidSDK.class));
            }
        });

    }
}