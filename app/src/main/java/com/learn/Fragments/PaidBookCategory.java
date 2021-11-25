package com.learn.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.renderscript.Sampler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learn.Adapters.SliderAdapter;
import com.learn.Models.BooksInfo;
import com.learn.booklistapp.InterestTopicsHome;
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentPaidBookCategoryBinding;

import java.net.URL;
import java.util.ArrayList;


public class PaidBookCategory extends Fragment {


    public PaidBookCategory() {
        // Required empty public constructor
    }

    FragmentPaidBookCategoryBinding binding;
    private static String Json_Link = "";
    ArrayList<BooksInfo> bookList;
    SliderAdapter mAdapter;
    int limit = 0;
    private static String LOG_TAG = PaidBookCategory.class.getSimpleName();
    private String tempLink = "";
    private ConnectivityManager cm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaidBookCategoryBinding.inflate(getLayoutInflater());

        Json_Link = getActivity().getIntent().getStringExtra("Category");
        Json_Link +="&filter=paid-ebooks&maxResults=20&startIndex=";
        tempLink = Json_Link;
        Json_Link += limit;
        bookList = new ArrayList<>();

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        UtilsAsyncTask task = new UtilsAsyncTask();
        task.execute();

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(binding.searchEdittext.getText().toString())){
                    Toast.makeText(getContext(),"Type something",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
                    binding.loadingSpinner.setVisibility(View.VISIBLE);
                    String searchText = binding.searchEdittext.getText().toString().trim();
                    String startL = "https://www.googleapis.com/books/v1/volumes?q=";
                    startL += searchText;
                    String end = "&filter=paid-ebooks&maxResults=20&startIndex=";
                    tempLink = startL + end;
                    limit = 0;
                    end += limit;
                    Json_Link = startL + end;

                    UtilsAsyncTask task1 = new UtilsAsyncTask();
                    task1.execute();
                }

            }
        });

        binding.nestedScrollViewPaid.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){

                    limit += 20;
                    Log.i(LOG_TAG,"LIMIT: "+limit);
                    Json_Link = tempLink;
                    Json_Link += limit;

                    binding.progressBarMoreResp.setVisibility(View.VISIBLE);
                    UtilsAsyncTaskMore task1 = new UtilsAsyncTaskMore();
                    task1.execute();

                    Log.i(LOG_TAG,"More Result Link: "+Json_Link);

                }
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //  fetchTimelineAsync(0);

                bookList.clear();
                Toast.makeText(getContext(),"Loading...",Toast.LENGTH_LONG).show();

                task.cancel(true);
                Json_Link = tempLink;
                limit = 0;
                Json_Link += limit;
                UtilsAsyncTask task1 = new UtilsAsyncTask();
                task1.execute();
                binding.swipeContainer.setRefreshing(false);

            }
        });

        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return binding.getRoot();
    }

    protected void updateUi(ArrayList<BooksInfo> booksInfos) {

        bookList = booksInfos;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            binding.emptyNoBook.setVisibility(View.VISIBLE);
        } else{
            binding.emptyNoBook.setVisibility(View.GONE);
        }

        SliderAdapter sliderAdapter = new SliderAdapter(booksInfos, binding.recyclerViewPaid, getContext(), R.layout.magzine_item, 2);
        binding.recyclerViewPaid.setAdapter(sliderAdapter);
        binding.recyclerViewPaid.setLayoutManager(new GridLayoutManager(getContext(), 2));


    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(Json_Link);            //also we can use  urls[0]
            Log.i(LOG_TAG,"Normal Link : "+ Json_Link);
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            binding.loadingSpinner.setVisibility(View.GONE);
         //   binding.progressBarMoreResp.setVisibility(View.GONE);

            if (event == null) {
                binding.emptyNoBook.setText("No Books Found");
                return;
            }

            updateUi(event);
        }
    }

    private class UtilsAsyncTaskMore extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(Json_Link);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

          //  binding.loadingSpinner.setVisibility(View.GONE);
            binding.progressBarMoreResp.setVisibility(View.GONE);

            if (event == null) {
                //  mEmptyStateTextView.setText("No Books Found");
                Toast.makeText(getContext(),"No More Books",Toast.LENGTH_SHORT).show();
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

        Log.i(LOG_TAG,"On Destroy the Book list is cleared");

    }
}