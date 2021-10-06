package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class BooksListResponse extends AppCompatActivity {

   // private static final String SAMPLE_ANDROID_JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&maxResults=20";
    private static String LOG_TAG = BooksListResponse.class.getSimpleName();
    private static final int QUERY_UTILS_LOADER_ID = 1;

    private static String JSON_RESPONSE;

   // private ListAdapter mAdapter;
    private ArrayList<BooksInfo> booksList;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_books_view);

        getSupportActionBar().hide();

        JSON_RESPONSE = getIntent().getStringExtra("Response_Link");

        UtilsAsyncTask task = new UtilsAsyncTask();
        task.execute();

       /* booksList = new ArrayList<>();
        mAdapter = new ListAdapter(BooksListResponse.this,booksList);*/

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.loading_spinner);

    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        ListAdapter itemAdapter = new ListAdapter(BooksListResponse.this,booksInfos);
       // booksList = booksInfos;

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(itemAdapter);
       // earthquakeListView.setAdapter(mAdapter);
       // Log.d(LOG_TAG,""+booksList.size());
        bookListView.setEmptyView(mEmptyStateTextView);
        Log.i(LOG_TAG,"Updating UI");

        itemAdapter.notifyDataSetChanged();

         bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 BooksInfo currBook = itemAdapter.getItem(position);

                     Intent i = new Intent(BooksListResponse.this, BooksDetails.class);

                     i.putExtra("tittle", currBook.getBookTitle());
                     i.putExtra("publisher", currBook.getPublisher());
                     i.putExtra("publishingDate", currBook.getPublishingDate());
                     i.putExtra("description", currBook.getDescription());
                     i.putExtra("pageCount", currBook.getPageCount());
                     i.putExtra("authors", currBook.getAuthors());
                     i.putExtra("thumbnailLink", currBook.getThumbnailLink());
                     i.putExtra("language", currBook.getLanguage());
                     i.putExtra("previewLink", currBook.getPreviewLink());
                     i.putExtra("buyingLink", currBook.getBuyingLink());
                     i.putExtra("rating", currBook.getRating());
                     i.putExtra("ratingCount", currBook.getRatingCount());

                     startActivity(i);
                 }


         });

    }

    private class UtilsAsyncTask extends AsyncTask<URL,Void,ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event =  QueryUtils.fetchEarthquakeData(JSON_RESPONSE);            //also we can use  urls[0]
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