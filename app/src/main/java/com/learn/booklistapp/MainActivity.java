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

    private static final String SAMPLE_ANDROID_DEVELOPMENT_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&maxResults=20";
    private static final String SAMPLE_CRYPTOGRAPHY_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=cryptography&maxResults=20";
    private static final String SAMPLE_CYBER_SECURITY_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=cyberSecurity&maxResults=20";
    private static final String SAMPLE_WEB_DEVELOPMENT_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=webDevelopment&maxResults=20";
    private static final String SAMPLE_CLOUD_DEVELOPMENT_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=cloudDevelopment&maxResults=20";
    private static final String SAMPLE_MACHINE_LEARNING_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=machineLearning&maxResults=20";
    private static final String SAMPLE_DATA_STORAGE_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=dataBase&maxResults=20";

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
                  Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                  i.putExtra("Response_Link",SAMPLE_ANDROID_DEVELOPMENT_JSON_RESPONSE);
                  startActivity(i);
             }
         });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_WEB_DEVELOPMENT_JSON_RESPONSE);
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
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_CLOUD_DEVELOPMENT_JSON_RESPONSE);
                startActivity(i);
            }
        });

        cryptography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_CRYPTOGRAPHY_JSON_RESPONSE);
                startActivity(i);
            }
        });

        dataStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_DATA_STORAGE_JSON_RESPONSE);
                startActivity(i);
            }
        });

        machineLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_MACHINE_LEARNING_JSON_RESPONSE);
                startActivity(i);
            }
        });

        cyberSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BooksListResponse.class);
                i.putExtra("Response_Link",SAMPLE_CYBER_SECURITY_JSON_RESPONSE);
                startActivity(i);
            }
        });

    }
}