package com.learn.booklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.learn.Fragments.CategoriesFragment;
import com.learn.Fragments.FreeBooksCategory;
import com.learn.Fragments.HomeFragment;
import com.learn.Fragments.MagazinesFragment;
import com.learn.Fragments.PaidBookCategory;
import com.learn.booklistapp.databinding.ActivityCategoriesResultBinding;

public class CategoriesResultActivity extends AppCompatActivity {


    ActivityCategoriesResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new PaidBookCategory());
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


                    case R.id.paidBooks:
                        transaction.replace(R.id.main_content,new PaidBookCategory());
                        break;

                    case R.id.freeBooks:
                        transaction.replace(R.id.main_content,new FreeBooksCategory());
                        break;

                }

                transaction.commit();
                return true;

            }
        });


    }


}