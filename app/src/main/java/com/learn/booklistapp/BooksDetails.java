package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.learn.booklistapp.databinding.ActivityBooksDetailsBinding;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class BooksDetails extends AppCompatActivity {

    ActivityBooksDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBooksDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getSupportActionBar().hide();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String bookTitle = getIntent().getStringExtra("tittle");
        String publisher = getIntent().getStringExtra("publisher");
        String publishingDate = getIntent().getStringExtra("publishingDate");
        String description = getIntent().getStringExtra("description");
        int pageCount = getIntent().getIntExtra("pageCount", 0);
        ArrayList authors = getIntent().getStringArrayListExtra("authors");
        String thumbnailLink = getIntent().getStringExtra("thumbnailLink");
        String language = getIntent().getStringExtra("language");
        String previewLink = getIntent().getStringExtra("previewLink");
        String buyingLink = getIntent().getStringExtra("buyingLink");
        double rating = getIntent().getDoubleExtra("rating", 0);
        int ratingCount = getIntent().getIntExtra("ratingCount", 0);

        /*Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);*/

        if(bookTitle!=null)
        binding.bookNameDetails.setText(bookTitle);
        if(publisher!=null)
        binding.publisher.setText("Published by:\n"+publisher);

        if(authors!=null) {
            StringBuilder authorsName = new StringBuilder("Authors:\n");
            for (int i = 0; i < authors.size(); i++) {
                if (i != authors.size() - 1)
                    authorsName.append(authors.get(i) + " , ");
                else
                    authorsName.append(authors.get(i));
            }
            binding.authorsDetails.setText(authorsName.toString());
        }

       // if(publishingDate!=null)
       // binding.publisingDate.setText(publishingDate);

        if(description!=null)
        binding.description.setText(description);

       // binding.pageCount.setText(""+pageCount);

        //set image we need to do
       /* try {
            URL url = new URL(thumbnailLink);
            Glide.with(BooksDetails.this).load(Uri.parse(String.valueOf(url))).into(binding.bookImageDetails);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

        if(language!=null)
        binding.language.setText(language);


            binding.previewLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(previewLink != null) {
                        Uri uri = Uri.parse(previewLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Sorry Preview Link is Not Available",Toast.LENGTH_SHORT).show();
                    }
                }
            });
           // binding.previewLink.setAnimation(anim);


            binding.buyingLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("bookDetails","buyLInk");
                    if(buyingLink!=null) {
                        Uri uri = Uri.parse(buyingLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else {
                        Log.i("bookDetails","buyLinjsihfi");
                        Toast.makeText(getApplicationContext(),"Sorry Buying Link is Not Available",Toast.LENGTH_SHORT).show();
                    }
                }
            });
           // binding.buyingLink.setAnimation(anim);

        try {
            if(thumbnailLink!=null) {
                Glide.with(getApplicationContext())
                        .load(new URL(thumbnailLink))
                        .into(binding.bookImageDetails);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        binding.ratings.setText(""+rating);
        binding.ratingCount.setText(""+ratingCount);

        /*Integer colorFrom = getResources().getColor(R.color.blue);
        Integer colorTo = getResources().getColor(R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                binding.previewLink.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        colorAnimation.start();*/


    }
}