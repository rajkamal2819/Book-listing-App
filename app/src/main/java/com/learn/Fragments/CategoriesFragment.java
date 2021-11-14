package com.learn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn.Adapters.CategoryAdapter;
import com.learn.Models.Categories;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentCategoriesBinding;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {

    public CategoriesFragment() {
        // Required empty public constructor
    }

    FragmentCategoriesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(getLayoutInflater());

        ArrayList<Categories> categoriesArrayList = new ArrayList<>();

        categoriesArrayList.add(new Categories(R.drawable.action_adventure_icon,"Action and\nAdventure"));
        categoriesArrayList.add( new Categories(R.drawable.classic_icon,"Classic"));
        categoriesArrayList.add( new Categories(R.drawable.detective,"Thrillers and\nCrimes"));
        categoriesArrayList.add( new Categories(R.drawable.comic,"Comics"));
        categoriesArrayList.add( new Categories(R.drawable.fantasty,"Science\nFiction"));
        categoriesArrayList.add( new Categories(R.drawable.biography,"Auto\nBiography"));
        categoriesArrayList.add( new Categories(R.drawable.historic,"Historic"));
        categoriesArrayList.add( new Categories(R.drawable.horror,"Horror"));
        categoriesArrayList.add( new Categories(R.drawable.sports,"Sports"));
        categoriesArrayList.add( new Categories(R.drawable.other_books,"Others"));

        CategoryAdapter adapter = new CategoryAdapter(categoriesArrayList,binding.categoriesRecyclerView,getContext());
        binding.categoriesRecyclerView.setAdapter(adapter);
        binding.categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return binding.getRoot();
    }
}