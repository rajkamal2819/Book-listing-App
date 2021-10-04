package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AndroidDevBooks extends AppCompatActivity {

    private static final String SAMPLE_ANDROID_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&maxResults=10";
    private static String LOG_TAG = AndroidDevBooks.class.getSimpleName();
    private static final int QUERY_UTILS_LOADER_ID = 1;

    private ListAdapter mAdapter;
    private ArrayList<BooksInfo> booksList;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_books_view);

        getSupportActionBar().hide();

        UtilsAsyncTask task = new UtilsAsyncTask();
        task.execute();

       /* booksList = new ArrayList<>();
        mAdapter = new ListAdapter(AndroidDevBooks.this,booksList);*/

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.loading_spinner);

    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        ListAdapter itemAdapter = new ListAdapter(AndroidDevBooks.this,booksInfos);
       // booksList = booksInfos;

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
         earthquakeListView.setAdapter(itemAdapter);
       // earthquakeListView.setAdapter(mAdapter);
       // Log.d(LOG_TAG,""+booksList.size());
        earthquakeListView.setEmptyView(mEmptyStateTextView);


        Log.i(LOG_TAG,"Updating UI");

       /* earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Report currQuake = mAdapter.getItem(position);
                String url = currQuake.getUrl();
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currQuake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });*/

    }

    private class UtilsAsyncTask extends AsyncTask<URL,Void,ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event =  QueryUtils.fetchEarthquakeData(SAMPLE_ANDROID_JSON_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

           /* mEmptyStateTextView.setText("No Earthquakes Found");
            progressBar.setVisibility(View.GONE);
            mAdapter.clear();

            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();*/

            /*if(!isConnected){
                mEmptyStateTextView.setText("No Internet Connection");
            }*/

            progressBar.setVisibility(View.GONE);

            if(event==null){
                return;
            }

            mEmptyStateTextView.setText("No Books Found");
            updateUi(event);

        }

    }

}