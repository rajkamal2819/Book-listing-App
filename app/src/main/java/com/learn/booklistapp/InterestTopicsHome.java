package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.learn.Adapters.SliderAdapter;
import com.learn.Fragments.PaidBookCategory;
import com.learn.Models.BooksInfo;
import com.learn.booklistapp.databinding.ActivityIntrestTopicsHomeBinding;

import java.net.URL;
import java.util.ArrayList;

public class InterestTopicsHome extends AppCompatActivity {

    ActivityIntrestTopicsHomeBinding binding;
    private static String SAMPLE_RESPONSE_LINK = "";
    int limit = 0;
    ArrayList<BooksInfo> bookList;
    private String tempLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntrestTopicsHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String jsonStart = getIntent().getStringExtra("jsonStart");
        String search = getIntent().getStringExtra("search");
        String jsonEnd = getIntent().getStringExtra("jsonEnd");
        boolean isSearched = getIntent().getBooleanExtra("isSearched", false);

        bookList = new ArrayList<>();

        if (isSearched)
            SAMPLE_RESPONSE_LINK = jsonStart + search + jsonEnd;
        else
            SAMPLE_RESPONSE_LINK = jsonStart + "Stories"+ jsonEnd;

        tempLink = SAMPLE_RESPONSE_LINK;
        SAMPLE_RESPONSE_LINK += limit;
        Log.i("lINK: ", SAMPLE_RESPONSE_LINK);
        Log.i("isSearched: ", isSearched + "");

        UtilsAsyncTask task = new UtilsAsyncTask();
        task.execute();

        binding.backArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.nestedScrollViewInterests.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    limit += 20;
                    // Log.i(LOG_TAG,"LIMIT: "+limit);
                    SAMPLE_RESPONSE_LINK = tempLink;
                    SAMPLE_RESPONSE_LINK += limit;

                    binding.progressBar2.setVisibility(View.VISIBLE);
                    UtilsAsyncTaskMore task1 = new UtilsAsyncTaskMore();
                    task1.execute();

                    //  Log.i(LOG_TAG,"More Result Link: "+Json_Link);

                }
            }
        });

    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {

        bookList = booksInfos;

        SliderAdapter sliderAdapter = new SliderAdapter(booksInfos, binding.recyclerViewInterest, getApplicationContext(), R.layout.magzine_item, 2);
        binding.recyclerViewInterest.setAdapter(sliderAdapter);
        binding.recyclerViewInterest.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_RESPONSE_LINK);            //also we can use  urls[0]
            // Log.i(LOG_TAG,"Normal Link : "+ Json_Link);
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            binding.progressBar1.setVisibility(View.GONE);
            //   binding.progressBarMoreResp.setVisibility(View.GONE);

            if (event == null) {
                //  mEmptyStateTextView.setText("No Books Found");
                return;
            }

            updateUi(event);
        }
    }

    private class UtilsAsyncTaskMore extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_RESPONSE_LINK);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            //  binding.loadingSpinner.setVisibility(View.GONE);
            binding.progressBar2.setVisibility(View.GONE);

            if (event == null) {
                //  mEmptyStateTextView.setText("No Books Found");
                Toast.makeText(getApplicationContext(),"No More Books",Toast.LENGTH_SHORT).show();
                return;
            }

            bookList.addAll(event);
            updateUi(bookList);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookList.clear();
    }
}