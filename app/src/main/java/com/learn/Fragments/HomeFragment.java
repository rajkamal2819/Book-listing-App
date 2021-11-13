package com.learn.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.Adapters.Slider2Adapter;
import com.learn.booklistapp.BooksInfo;
import com.learn.booklistapp.MagazinesActivity;
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentHomeBinding;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
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
    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=Stories&maxResults=20";
    private String SAMPLE_Json_RESPONSE_Newest = "https://www.googleapis.com/books/v1/volumes?q=Stories&maxResults=20&orderBy=newest";
    private String SAMPLE_Json_RESPONSE_EBooks = "https://www.googleapis.com/books/v1/volumes?q=Stories&maxResults=20&filter=free-ebooks";
    private String SAMPLE_Json_RESPONSE_Magazines = "https://www.googleapis.com/books/v1/volumes?q=Stories&maxResults=20&printType=magazines";
    private static final int BOOK_LOADER_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

            HomeAsyncTask task = new HomeAsyncTask();
        try {
            task.execute(new URL(SAMPLE_Json_RESPONSE),new URL(SAMPLE_Json_RESPONSE_Newest)
            ,new URL(SAMPLE_Json_RESPONSE_EBooks),new URL(SAMPLE_Json_RESPONSE_Magazines));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

            binding.loadingSpinner.setVisibility(View.VISIBLE);

            binding.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    binding.loadingSpinner.setVisibility(View.VISIBLE);
                    String interests = binding.edittextInterests.getText().toString().trim();
                    String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEnd =       "&orderBy=newest&maxResults=20";
                    JsonStart += binding.edittextInterests.getText().toString();
                    String JsonLink = JsonStart + JsonEnd;
                    SAMPLE_Json_RESPONSE = JsonLink;

                    String JsonStartNew = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEndNew =       "&orderBy=newest&maxResults=20&orderBy=newest";
                    JsonStartNew += binding.edittextInterests.getText().toString().trim();
                    String JsonLinkNew = JsonStartNew + JsonEndNew;
                    SAMPLE_Json_RESPONSE_Newest = JsonLinkNew;

                    String JsonStartEbooks = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEndEBooks =       "&orderBy=newest&maxResults=20&filter=free-ebooks";
                    JsonStartEbooks += binding.edittextInterests.getText().toString().trim();
                    String JsonLinkEBooks = JsonStartEbooks + JsonEndEBooks;
                    SAMPLE_Json_RESPONSE_EBooks = JsonLinkEBooks;

                    String JsonStartMagazines = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEndMagazines =       "&orderBy=newest&maxResults=20&printType=magazines";
                    JsonStartMagazines += binding.edittextInterests.getText().toString().trim();
                    String JsonLinkMag = JsonStartMagazines + JsonEndMagazines;
                    SAMPLE_Json_RESPONSE_Magazines = JsonLinkMag;

                    task.cancel(true);
                  //  task2.cancel(true);

                    HomeAsyncTask asyncTask = new HomeAsyncTask();
                    try {
                        asyncTask.execute(new URL(SAMPLE_Json_RESPONSE),new URL(SAMPLE_Json_RESPONSE_Newest)
                        ,new URL(SAMPLE_Json_RESPONSE_EBooks),new URL(SAMPLE_Json_RESPONSE_Magazines));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    Log.i("Response Link: ",JsonLink);
                   // binding.edittextInterests.setText("");
                    binding.edittextInterests.clearFocus();
                }
            });

            /*binding.Magzines.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*task.cancel(true);
                    task2.cancel(true);*//*
                    startActivity(new Intent(getContext(), MagazinesActivity.class));
                }
            });
*/
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

    private class HomeAsyncTask extends AsyncTask<URL, Void, ArrayList<ArrayList<BooksInfo>>> {
        @Override
        protected ArrayList<ArrayList<BooksInfo>> doInBackground(URL... urls) {

          //  ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(SAMPLE_Json_RESPONSE);   //also we can use  urls[0]

            ArrayList<ArrayList<BooksInfo>> events = new ArrayList<>();
            for (int i = 0;i<urls.length;i++){
               ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(urls[i].toString());
               events.add(event);
            }

            Log.d("HOme Fragment Utils",SAMPLE_Json_RESPONSE);
            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<BooksInfo>> events) {

            // progressBar.setVisibility(View.GONE);

            binding.loadingSpinner.setVisibility(View.GONE);

            if (events == null) {
                Log.e("HOme Fragment Event","NULL Event");
                return;
            }

            // Toast.makeText(getContext(), "Images Loaded", Toast.LENGTH_SHORT).show();

            //  mEmptyStateTextView.setText("No Books Found");
            //  updateUi(event);

            fetchSlider2Info(events.get(0));
            fetchImages(events.get(1));
            fetchEbooks(events.get(2));
            fetchMagazines(events.get(3));

        }

    }

    public void fetchEbooks(ArrayList<BooksInfo> infos){
        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        Slider2Adapter sliderAdapter = new Slider2Adapter(infos,binding.recyclerViewEBooks,getContext(),R.layout.slider2_container,1);
        binding.recyclerViewEBooks.setAdapter(sliderAdapter);
        binding.recyclerViewEBooks.setLayoutManager(horizontalManager2);
    }

    public void fetchMagazines(ArrayList<BooksInfo> infos){
        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        Slider2Adapter sliderAdapter = new Slider2Adapter(infos,binding.recyclerViewMagazinesHOme,getContext(),R.layout.slider2_container,1);
        binding.recyclerViewMagazinesHOme.setAdapter(sliderAdapter);
        binding.recyclerViewMagazinesHOme.setLayoutManager(horizontalManager2);
    }

    public void fetchImages(List<BooksInfo> infos){

        LinearLayoutManager horizontalManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        Slider2Adapter adapter = new Slider2Adapter(infos,binding.recyclerView2,getContext(),R.layout.slider_item_container,3);
        binding.recyclerView2.setAdapter(adapter);
        binding.recyclerView2.setLayoutManager(horizontalManager);

    }

    public void fetchSlider2Info(List<BooksInfo> booksInfo){

        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        Slider2Adapter sliderAdapter = new Slider2Adapter(booksInfo,binding.recyclerView,getContext(),R.layout.slider2_container,1);
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
