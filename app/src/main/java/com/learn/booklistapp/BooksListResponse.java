package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.learn.booklistapp.databinding.ActivityBooksListResponseBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BooksListResponse extends AppCompatActivity {

    private static String LOG_TAG = BooksListResponse.class.getSimpleName();
    private static final int QUERY_UTILS_LOADER_ID = 1;

    private static String JSON_RESPONSE;

    // private ListAdapter mAdapter;
    private ArrayList<BooksInfo> booksList;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;

   /* boolean isScrolling = false;
    int currentItem, totalItem, scrollOutItems; */
    static int indexCount = 0;
    UtilsAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_books_view);

        // getSupportActionBar().hide();

        JSON_RESPONSE = getIntent().getStringExtra("Response_Link");
        JSON_RESPONSE += "&startIndex=" + indexCount;

        task = new UtilsAsyncTask();
        task.execute();

       /* booksList = new ArrayList<>();
        mAdapter = new ListAdapter(BooksListResponse.this,booksList);*/
       // Log.e(LOG_TAG, "current Count = " + currentItem + " , totalItem = " + totalItem + " , scrollOutItems = " + scrollOutItems);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.loading_spinner);

    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        ListAdapter itemAdapter = new ListAdapter(BooksListResponse.this, booksInfos);
        bookListView.setAdapter(itemAdapter);

        bookListView.setEmptyView(mEmptyStateTextView);
        Log.i(LOG_TAG, "Updating UI");

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

/*        bookListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

               if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                   //this line specifies that our Scrolling state had been changed and now we need to update the isScrolling variable
                   isScrolling = true;
               }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                currentItem = visibleItemCount;
                totalItem = totalItemCount;
                scrollOutItems = firstVisibleItem;
                Log.e(LOG_TAG,"current Count = "+currentItem+" , totalItem = "+totalItem+" , scrollOutItems = "+scrollOutItems);

                if(isScrolling&&(currentItem+scrollOutItems==totalItem)){
                       indexCount += 21;


                }

            }
        });  */


    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(JSON_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            progressBar.setVisibility(View.GONE);

            if (event == null) {
                return;
            }

            mEmptyStateTextView.setText("No Books Found");
            updateUi(event);

        }


    }
}