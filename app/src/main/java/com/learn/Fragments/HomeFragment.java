package com.learn.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentHomeBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase database;
    FirebaseAuth auth;

    FragmentHomeBinding binding;
    boolean isJsonReady = false;
    private String SAMPLE_Json_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=Litrature&maxResults=20";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

            HomeAsyncTask task = new HomeAsyncTask();
            task.execute();

            binding.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String interests = binding.edittextInterests.getText().toString();
                    String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                    String JsonEnd =       "&orderBy=newest&maxResults=20";
                    JsonStart += binding.edittextInterests.getText().toString();
                    String JsonLink = JsonStart + JsonEnd;
                    SAMPLE_Json_RESPONSE = JsonLink;
                    HomeAsyncTask asyncTask = new HomeAsyncTask();
                    asyncTask.execute();
                    Log.i("Response Link: ",JsonLink);
                    binding.edittextInterests.setText("");
                }
            });

        return binding.getRoot();
    }

   /* public void giveRequiredJsonLink(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                .getUid()).child("interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
                String JsonEnd =       "&orderBy=newest&maxResults=20";
                interests.setInterests(snapshot.getValue(String.class));
              *//*  String searchText = snapshot.getValue(String.class);
                JsonStart += searchText;
                String JsonLink = JsonStart + JsonEnd;*//*

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/

     /*public void giveRequiredJsonLink(){

       String interests = binding.edittextInterests.getText().toString();
         String JsonStart = "" + "https://www.googleapis.com/books/v1/volumes?q=";
         String JsonEnd =       "&orderBy=newest&maxResults=20";
         JsonStart =
         String JsonLink = JsonStart + JsonEnd;

    }*/

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

        SliderAdapter sliderAdapter = new SliderAdapter(infos,binding.viewpager2,getContext());
        binding.viewpager2.setAdapter(sliderAdapter);

        binding.viewpager2.setClipToPadding(false);
        binding.viewpager2.setClipChildren(false);
        binding.viewpager2.setOffscreenPageLimit(3);
        binding.viewpager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

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

        float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        binding.viewpager2.setPageTransformer((page, position) -> {
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

    }

    public void fetchSlider2Info(List<BooksInfo> booksInfo){

        Slider2Adapter sliderAdapter = new Slider2Adapter(booksInfo,binding.viewpager3,getContext());
        binding.viewpager3.setAdapter(sliderAdapter);

        binding.viewpager3.setClipToPadding(false);
        binding.viewpager3.setClipChildren(false);
        binding.viewpager3.setOffscreenPageLimit(3);
        binding.viewpager3.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

    }

}