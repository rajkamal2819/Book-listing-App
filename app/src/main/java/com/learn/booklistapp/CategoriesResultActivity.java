package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.learn.Adapters.FragmentAdapter;
import com.learn.booklistapp.databinding.ActivityCategoriesResultBinding;

public class CategoriesResultActivity extends AppCompatActivity {


    ActivityCategoriesResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPagerMoreCourses.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayoutMoreCourses.setupWithViewPager(binding.viewPagerMoreCourses);
        binding.tabLayoutMoreCourses.setTabGravity(TabLayout.GRAVITY_FILL);

    }


}