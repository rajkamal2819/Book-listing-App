package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.booklistapp.databinding.ActivityOtherBooksBinding;

import java.net.URL;
import java.util.ArrayList;

public class OtherBooks extends AppCompatActivity {

    ActivityOtherBooksBinding binding;
    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20";
    private static String Log_tag = OtherBooks.class.getSimpleName();

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
        binding = ActivityOtherBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //length till equal to char is 46
        Log.d(Log_tag,SAMPLE_Json_RESPONSE);
        SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20";
        SAMPLE_Json_RESPONSE += "&startIndex="+indexCounter;

        progressBar = findViewById(R.id.loading_spinner);

        Log.e(Log_tag,"Index Counter = "+indexCounter);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SAMPLE_Json_RESPONSE = "" +
                        "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20";
                String searchText = "";
                searchText = binding.searchEdittext.getText().toString();

                StringBuilder str = new StringBuilder(SAMPLE_Json_RESPONSE);
                str.insert(46,searchText);

                SAMPLE_Json_RESPONSE = str.toString();
                SAMPLE_Json_RESPONSE += "&startIndex="+indexCounter;
                Log.e(Log_tag,"Index Counter = "+indexCounter);

                progressBar.setVisibility(View.VISIBLE);
                UtilsAsyncTask task = new UtilsAsyncTask();
                Log.d(Log_tag,SAMPLE_Json_RESPONSE);
                task.execute();
                isExecuted = true;
            }
        });



            binding.nextItemsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isExecuted) {
                    indexCounter += 21;
                    SAMPLE_Json_RESPONSE = "" +
                            "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20";
                    String searchText = "";
                    searchText = binding.searchEdittext.getText().toString();

                    StringBuilder str = new StringBuilder(SAMPLE_Json_RESPONSE);
                    str.insert(46, searchText);

                    SAMPLE_Json_RESPONSE = str.toString();
                    SAMPLE_Json_RESPONSE += "&startIndex=" + indexCounter;

                    Log.e(Log_tag, "Index Counter = " + indexCounter);

                    UtilsAsyncTask task = new UtilsAsyncTask();
                    task.execute();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please First Type and Search Some Books",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view_others);


    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.listView);

        // Create a new {@link ArrayAdapter} of earthquakes
        ListAdapter itemAdapter = new ListAdapter(OtherBooks.this,booksInfos);
        // booksList = booksInfos;

        bookListView.setAdapter(itemAdapter);
        bookListView.setEmptyView(mEmptyStateTextView);

        itemAdapter.notifyDataSetChanged();

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BooksInfo currBook = itemAdapter.getItem(position);

                Intent i = new Intent(OtherBooks.this, BooksDetails.class);

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
            ArrayList<BooksInfo> event =  QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            progressBar.setVisibility(View.GONE);

            if(event==null){
                return;
            }

            mEmptyStateTextView.setText("No Books Found");
            updateUi(event);

        }

    }

}