package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout android;
    LinearLayout web;
    LinearLayout cryptography;
    LinearLayout machineLearning;
    LinearLayout cloudDev;
    LinearLayout cyberSecurity;
    LinearLayout others;
    LinearLayout dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         android = findViewById(R.id.android_dev);
         web = findViewById(R.id.web_dev);
         cryptography = findViewById(R.id.cryptography);
         machineLearning = findViewById(R.id.manchine_learning);
         cloudDev = findViewById(R.id.cloud_dev);
         cyberSecurity = findViewById(R.id.cyber_security);
         others = findViewById(R.id.others);
         dataStorage = findViewById(R.id.datastorage);

         android.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  Intent i = new Intent(MainActivity.this,AndroidDevBooks.class);
                  startActivity(i);
             }
         });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,WebDevBooks.class);
                startActivity(i);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,OtherBooks.class);
                startActivity(i);
            }
        });

        cloudDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CloudDevBooks.class);
                startActivity(i);
            }
        });

        cryptography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CryptographyBooks.class);
                startActivity(i);
            }
        });

        dataStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DataStorageBooks.class);
                startActivity(i);
            }
        });

        machineLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MachineLearningBooks.class);
                startActivity(i);
            }
        });

        cyberSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CyberSecurity.class);
                startActivity(i);
            }
        });

    }
}