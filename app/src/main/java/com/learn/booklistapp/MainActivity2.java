package com.learn.booklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;
import com.learn.Adapters.SliderAdapter;
import com.learn.Fragments.CategoriesFragment;
import com.learn.Fragments.HomeFragment;
import com.learn.Fragments.ProfileFragment;
import com.learn.booklistapp.databinding.ActivityMain2Binding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    private static final String JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&orderBy=newest&maxResults=20";
    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new HomeFragment());
        homeTransaction.commit();

        /* ------------------------Bottom normal Navigation Bar replacing with Bubble navigation bar----------------------*/

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    /**
                     * if we are in Activity then so i used getSupportFragmentManger()
                     * else if we were in fragment then use getFragmentManager()
                     */


                    case R.id.home:
                        transaction.replace(R.id.main_content,new HomeFragment());
                        break;

                    case R.id.categories:
                        transaction.replace(R.id.main_content,new CategoriesFragment());
                        break;

                    case R.id.profile:
                        transaction.replace(R.id.main_content,new ProfileFragment());
                        break;

                }

                transaction.commit();
                return true;

            }
        });


    }

}