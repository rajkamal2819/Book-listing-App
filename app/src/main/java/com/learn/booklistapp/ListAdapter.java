package com.learn.booklistapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ListAdapter extends ArrayAdapter<BooksInfo> {


    public ListAdapter(@NonNull Context context, @NonNull ArrayList<BooksInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_android_dev_books, parent, false);
        }

        BooksInfo currentBook = getItem(position);

        TextView bookTittle = listItemView.findViewById(R.id.book_title_textview);
        bookTittle.setText(currentBook.getBookTitle());

        TextView authorName = listItemView.findViewById(R.id.author_textView);
        authorName.setText("By:- "+currentBook.getAuthors().get(0));

        ImageView bookImg = listItemView.findViewById(R.id.book_image);

        try {
            URL url = new URL(currentBook.getThumbnailLink());
            Glide.with(getContext()).load(Uri.parse("http://books.google.com/books/content?id=W585EAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api" )).into(bookImg);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

       // RelativeLayout relativeLayout = listItemView.findViewById(R.id.relativeLayout);

       // int magnitudeColor = getMagnitudeColor(position);
       // relativeLayout.setBackgroundColor(magnitudeColor);

        return listItemView;

    }

    protected int getMagnitudeColor(int pos){
        int magnitudeColorResourceId;

        //int randomNum = ThreadLocalRandom.current().nextInt(0,  pos);
        int randomNum = 0;

        switch (randomNum) {             //switch case cannot hold double value so we converted it to int fist
            case 0:
                magnitudeColorResourceId = R.color.green;
                break;
            case 1:
                magnitudeColorResourceId = R.color.light_yellow;
                break;
            case 2:
                magnitudeColorResourceId = R.color.blue;
                break;
            case 3:
                magnitudeColorResourceId = R.color.red;
                break;
            case 4:
                magnitudeColorResourceId = R.color.purple;
                break;
            default:
                magnitudeColorResourceId = R.color.green;
                break;
        }
        //since till here only we have given the right color location because the R.id gives the int location of that color id and
        //now we need to convert  it to actual color value
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }

}
