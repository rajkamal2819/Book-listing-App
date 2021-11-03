package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.Adapters.Slider2Adapter;
import com.learn.booklistapp.databinding.ActivityMagazinesBinding;

import java.net.URL;
import java.util.ArrayList;

public class MagazinesActivity extends AppCompatActivity {


    ActivityMagazinesBinding binding;
    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20&printType=magazines";
    private static String Log_tag = MagazinesActivity.class.getSimpleName();

    private ArrayList<BooksInfo> booksList;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;
    private ListAdapter mAdapter;
    // int counterItem = 20;
    private int indexCounter = 0;
    boolean isExecuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMagazinesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //length till equal to char is 46
        Log.d(Log_tag, SAMPLE_Json_RESPONSE);
        SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20&printType=magazines";
        SAMPLE_Json_RESPONSE += "&startIndex=" + indexCounter;

        progressBar = binding.progressSpineer;

        Log.e(Log_tag, "Index Counter = " + indexCounter);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*SAMPLE_Json_RESPONSE = "" +
                        "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20&printType=magazines";
                String searchText = "";
                searchText = binding.searchEdittext.getText().toString();

                StringBuilder str = new StringBuilder(SAMPLE_Json_RESPONSE);
                str.insert(46, searchText);

                SAMPLE_Json_RESPONSE = str.toString();*/

                String startJson = "https://www.googleapis.com/books/v1/volumes?q=";
                String endJson = "&maxResults=20&printType=magazines";
                String searchText = "";
                searchText = binding.searchEdittext.getText().toString();
                startJson += searchText;

                SAMPLE_Json_RESPONSE = startJson + endJson;

                SAMPLE_Json_RESPONSE += "&startIndex=" + indexCounter;
                Log.e(Log_tag, "Index Counter = " + indexCounter);

                progressBar.setVisibility(View.VISIBLE);
                UtilsAsyncTask task = new UtilsAsyncTask();
                Log.d(Log_tag, SAMPLE_Json_RESPONSE);
                task.execute();
                isExecuted = true;
            }
        });


        binding.nextItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExecuted) {
                    indexCounter += 21;
                    /*SAMPLE_Json_RESPONSE = "" +
                            "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20";
                    String searchText = "";
                    searchText = binding.searchEdittext.getText().toString();

                    StringBuilder str = new StringBuilder(SAMPLE_Json_RESPONSE);
                    str.insert(46, searchText);

                    SAMPLE_Json_RESPONSE = str.toString();*/

                    String startJson = "https://www.googleapis.com/books/v1/volumes?q=";
                    String endJson = "&maxResults=20&printType=magazines";
                    String searchText = "";
                    searchText = binding.searchEdittext.getText().toString();
                    startJson += searchText;

                    SAMPLE_Json_RESPONSE = startJson + endJson;

                    SAMPLE_Json_RESPONSE += "&startIndex=" + indexCounter;

                    Log.e(Log_tag, "Index Counter = " + indexCounter);

                    UtilsAsyncTask task = new UtilsAsyncTask();
                    task.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Please First Type and Search Some Books", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mEmptyStateTextView = binding.emptyNoBook;


    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {

        Slider2Adapter sliderAdapter = new Slider2Adapter(booksInfos,binding.recyclerView,getApplicationContext(),R.layout.magzine_item,2);
        binding.recyclerView.setAdapter(sliderAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));


    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            progressBar.setVisibility(View.GONE);

            if (event == null) {
                mEmptyStateTextView.setText("No Books Found");
                return;
            }

            updateUi(event);

        }

    }
}