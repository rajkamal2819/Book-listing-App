package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.learn.booklistapp.databinding.ActivityBooksDetailsBinding;

import java.util.ArrayList;

public class BooksDetails extends AppCompatActivity {

    ActivityBooksDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBooksDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getSupportActionBar().hide();

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
        int rating = getIntent().getIntExtra("rating", 0);
        int ratingCount = getIntent().getIntExtra("ratingCount", 0);

        /*Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);*/

        if(bookTitle!=null)
        binding.bookNameDetails.setText(bookTitle);
        if(publisher!=null)
        binding.publisher.setText(publisher);

        if(authors!=null) {
            StringBuilder authorsName = new StringBuilder();
            for (int i = 0; i < authors.size(); i++) {
                if (i != authors.size() - 1)
                    authorsName.append(authors.get(i) + " , ");
                else
                    authorsName.append(authors.get(i));
            }
            binding.authorsDetails.setText(authorsName.toString());
        }

        if(publishingDate!=null)
        binding.publisingDate.setText(publishingDate);

        if(description!=null)
        binding.description.setText(description);

        binding.pageCount.setText(""+pageCount);

        //set image we need to do

        if(language!=null)
        binding.language.setText(language);

        if(previewLink!=null) {
            binding.previewLink.setText(previewLink);
           // binding.previewLink.setAnimation(anim);
        }

        if(buyingLink!=null) {
            binding.buyingLink.setText(buyingLink);
           // binding.buyingLink.setAnimation(anim);
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