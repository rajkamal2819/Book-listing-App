package com.learn.booklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.learn.Fragments.CategoriesFragment;
import com.learn.Fragments.HomeFragment;
import com.learn.Fragments.MagazinesFragment;
import com.learn.booklistapp.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {


    private static final String JSON_RESPONSE = "https://www.googleapis.com/books/v1/volumes?q=androidDevelopment&orderBy=newest&maxResults=20";
    ActivityMain2Binding binding;

    private HomeFragment homeFragment;
    private CategoriesFragment categoriesFragment;
    private MagazinesFragment magazinesFragment;
    private final String HOME_FRAGMENT_TAG = "homeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        magazinesFragment = new MagazinesFragment();
        homeFragment = new HomeFragment();
        categoriesFragment = new CategoriesFragment();

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new HomeFragment());
        homeTransaction.commit();



       /* // ... fragment lookup or instantation from above...
        // Always add a tag to a fragment being inserted into container
        if (!homeFragment.isInLayout()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, homeFragment, HOME_FRAGMENT_TAG)
                    .commit();
        }*/

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
                        transaction.replace(R.id.main_content,homeFragment);
                      //  transaction.replace(R.id.main_content,homeFragment);
                        break;

                    case R.id.categories:
                        transaction.replace(R.id.main_content,categoriesFragment);
                        break;

                    case R.id.magazines_menu:
                        transaction.replace(R.id.main_content,new MagazinesFragment());
                        break;

                }

                transaction.commit();
                return true;

            }
        });


    }

}