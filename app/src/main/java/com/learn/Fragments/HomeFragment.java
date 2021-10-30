package com.learn.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.Adapters.Slider2Adapter;
import com.learn.Adapters.SliderAdapter;
import com.learn.booklistapp.BooksInfo;
import com.learn.booklistapp.QueryUtilLoader;
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentHomeBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment /*implements LoaderManager.LoaderCallbacks<ArrayList<BooksInfo>>*/{

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase database;
    FirebaseAuth auth;

    FragmentHomeBinding binding;
    boolean isJsonReady = false;
    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=Litrature&maxResults=20";
    private static final int BOOK_LOADER_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

            HomeAsyncTask task = new HomeAsyncTask();
            task.execute();

            binding.loadingSpinner.setVisibility(View.VISIBLE);

            binding.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.loadingSpinner.setVisibility(View.VISIBLE);
                    String interests = binding.edittextInterests.getText().toString();
                    String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEnd =       "&orderBy=newest&maxResults=20";
                    JsonStart += binding.edittextInterests.getText().toString();
                    String JsonLink = JsonStart + JsonEnd;
                    SAMPLE_Json_RESPONSE = JsonLink;
                    HomeAsyncTask asyncTask = new HomeAsyncTask();
                    asyncTask.execute();
                    Log.i("Response Link: ",JsonLink);
                   // binding.edittextInterests.setText("");
                    binding.edittextInterests.clearFocus();
                }
            });

       /* LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interests = binding.edittextInterests.getText().toString();
                String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                String JsonEnd =       "&orderBy=newest&maxResults=20";
                JsonStart += binding.edittextInterests.getText().toString();
                String JsonLink = JsonStart + JsonEnd;
                SAMPLE_Json_RESPONSE = JsonLink;
                *//*HomeAsyncTask asyncTask = new HomeAsyncTask();
                asyncTask.execute();*//*

                    LoaderManager loaderManager = getLoaderManager();
                    Log.d(LOG_TAG, "initLoader is called ...");
                    loaderManager.initLoader(BOOK_LOADER_ID, null, HomeFragment.this);

                Log.i("Response Link: ",JsonLink);
                binding.edittextInterests.setText("");
            }
        });
        */

        return binding.getRoot();
    }

    private class HomeAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {

            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);   //also we can use  urls[0]
            Log.d("HOme Fragment Utils",SAMPLE_Json_RESPONSE);
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            // progressBar.setVisibility(View.GONE);

            binding.loadingSpinner.setVisibility(View.GONE);

            if (event == null) {
                Log.e("HOme Fragment Event","NULL Event");
                return;
            }

            // Toast.makeText(getContext(), "Images Loaded", Toast.LENGTH_SHORT).show();

            //  mEmptyStateTextView.setText("No Books Found");
            //  updateUi(event);
            fetchImages(event);

            fetchSlider2Info(event);

        }

    }

    public void fetchImages(List<BooksInfo> infos){

        LinearLayoutManager horizontalManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        SliderAdapter adapter = new SliderAdapter(infos,binding.recyclerView2,getContext());
        binding.recyclerView2.setAdapter(adapter);
        binding.recyclerView2.setLayoutManager(horizontalManager);

        /*binding.viewpager2.setClipToPadding(false);
        binding.viewpager2.setClipChildren(false);
        binding.viewpager2.setOffscreenPageLimit(3);
        binding.viewpager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);*/

       /*
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.1f+r+0.1f);
            }
        });

        binding.viewpager2.setPageTransformer(compositePageTransformer);
         */

        /*float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        binding.recyclerView2.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (position < -1) {
                page.setTranslationX(-myOffset);
            } else if (position <= 1) {
                float scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f));
                page.setTranslationX(myOffset);
                page.setScaleY(scaleFactor);
                page.setAlpha(scaleFactor);
            } else {
                page.setAlpha(0);
                page.setTranslationX(myOffset);
            }
        });
         */

    }

    public void fetchSlider2Info(List<BooksInfo> booksInfo){

        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        Slider2Adapter sliderAdapter = new Slider2Adapter(booksInfo,binding.recyclerView,getContext());
        binding.recyclerView.setAdapter(sliderAdapter);
        binding.recyclerView.setLayoutManager(horizontalManager2);

    }

    /*@NonNull
    @Override
    public Loader<ArrayList<BooksInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader is called...");
            
        return new QueryUtilLoader(getContext(),SAMPLE_Json_RESPONSE);+
    }*/

    /*@Override
    public void onLoadFinished(@NonNull Loader<ArrayList<BooksInfo>> loader, ArrayList<BooksInfo> booksInfos) {
        Log.d(LOG_TAG, "onLoadFinished is called...");

        // Hide the progressBar spinner after loading
       // View loadingIndicator = binding.loadingSpinner;
       // loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display
       // mEmptyListTextView.setText("No Courses");

        // Clear the adapter of previous earthquake data
       // mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        *//*if (courses != null && !courses.isEmpty()) {
            mAdapter.addAll(courses);
        }*//*

        if (booksInfos != null && !booksInfos.isEmpty()) {
            fetchImages(booksInfos);
            fetchSlider2Info(booksInfos);
        }

    }*/

   /* @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<BooksInfo>> loader) {

    }

    private boolean internetIsConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }*/

}
