package com.learn.booklistapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<BooksInfo> {

    Bitmap myBitmap;

    public ListAdapter(@NonNull Context context, @NonNull ArrayList<BooksInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_books_list_response, parent, false);
        }

        BooksInfo currentBook = getItem(position);

        TextView bookTittle = listItemView.findViewById(R.id.book_title_textview);
        bookTittle.setText(currentBook.getBookTitle());

        TextView authorName = listItemView.findViewById(R.id.author_textView);
        authorName.setText("By:- "+currentBook.getAuthors().get(0));

        ImageView bookImg = listItemView.findViewById(R.id.book_image);
        try {
            Glide.with(getContext())
                    .load(new URL(currentBook.getThumbnailLink()))
                    .into(bookImg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return listItemView;

    }

}
