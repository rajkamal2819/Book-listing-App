package com.learn.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learn.Adapters.SliderAdapter;
import com.learn.booklistapp.BooksInfo;
import com.learn.booklistapp.MainActivity2;
import com.learn.booklistapp.QueryUtils;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.ActivityMain2Binding;
import com.learn.booklistapp.databinding.FragmentHomeBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding binding;
    private static final String JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&orderBy=newest&maxResults=20";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        UtilsAsyncTask task = new UtilsAsyncTask();
        task.execute();

        return binding.getRoot();
    }

    private class UtilsAsyncTask extends AsyncTask<URL, Void, ArrayList<BooksInfo>> {
        @Override
        protected ArrayList<BooksInfo> doInBackground(URL... urls) {
            ArrayList<BooksInfo> event = QueryUtils.fetchEarthquakeData(JSON_RESPONSE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<BooksInfo> event) {

            // progressBar.setVisibility(View.GONE);

            if (event == null) {
                return;
            }

            Toast.makeText(getContext(), "Images Loaded", Toast.LENGTH_SHORT).show();

            //  mEmptyStateTextView.setText("No Books Found");
            //  updateUi(event);
            fetchImages(event);

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

}