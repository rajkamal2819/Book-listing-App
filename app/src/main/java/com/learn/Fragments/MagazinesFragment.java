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

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.Adapters.SliderAdapter;
import com.learn.Models.BooksInfo;
import com.learn.booklistapp.InterestTopicsHome;
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentMagzinesBinding;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;


public class MagazinesFragment extends Fragment {


    public MagazinesFragment() {
        // Required empty public constructor
    }

    FragmentMagzinesBinding binding;

    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20&printType=magazines";
    private static String Log_tag = MagazinesFragment.class.getSimpleName();

    private ProgressBar progressBar;
    // private ListAdapter mAdapter;
    // int counterItem = 20;
    private int limit = 0;
    private String tempLink = "";
    ArrayList<BooksInfo> bookList;
    private ConnectivityManager cm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagzinesBinding.inflate(getLayoutInflater());

        Log.d(Log_tag, SAMPLE_Json_RESPONSE);
        SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=&maxResults=20&printType=magazines";
        SAMPLE_Json_RESPONSE += "&startIndex=";
        tempLink = SAMPLE_Json_RESPONSE;
        SAMPLE_Json_RESPONSE += limit;
        bookList = new ArrayList<>();

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchMagazines.setVisibility(View.GONE);

                if(TextUtils.isEmpty(binding.searchEdittext.getText().toString())){
                    Toast.makeText(getContext(),"Type something",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();

                    binding.progressSpineer.setVisibility(View.VISIBLE);
                    String searchText = binding.searchEdittext.getText().toString().trim();
                    String startL = "https://www.googleapis.com/books/v1/volumes?q=";
                    startL += searchText;
                    String end = "&maxResults=20&printType=magazines&startIndex=";
                    tempLink = startL + end;
                    limit = 0;
                    end += limit;
                    SAMPLE_Json_RESPONSE = startL + end;

                    UtilsAsyncTask task1 = new UtilsAsyncTask();
                    task1.execute();
                    Log.i("MagzinesFragment", SAMPLE_Json_RESPONSE);
                }

            }
        });

        if(TextUtils.isEmpty(binding.searchEdittext.getText())){
            binding.swipeContainer.setRefreshing(false);
        }
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    binding.progressSpineer2.setVisibility(View.VISIBLE);

                    limit += 20;
                    SAMPLE_Json_RESPONSE = tempLink;

                    SAMPLE_Json_RESPONSE += limit;

                    binding.progressSpineer2.setVisibility(View.VISIBLE);
                    UtilsAsyncTaskMore task1 = new UtilsAsyncTaskMore();
                    task1.execute();
                    Log.i("MagzinesFragment",SAMPLE_Json_RESPONSE);

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
                SAMPLE_Json_RESPONSE = tempLink;
                limit = 0;
                SAMPLE_Json_RESPONSE += limit;
                UtilsAsyncTask task1 = new UtilsAsyncTask();
                task1.execute();
                binding.swipeContainer.setRefreshing(false);

            }
        });

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

        SliderAdapter sliderAdapter = new SliderAdapter(booksInfos, binding.recyclerView, getContext(), R.layout.magzine_item, 2);
        binding.recyclerView.setAdapter(sliderAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            binding.progressSpineer.setVisibility(View.GONE);

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
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            //  binding.loadingSpinner.setVisibility(View.GONE);
            binding.progressSpineer2.setVisibility(View.GONE);
            if (event == null) {
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
    }
}