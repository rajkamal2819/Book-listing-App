package com.learn.booklistapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import com.learn.Models.BooksInfo;

import java.util.ArrayList;

public class QueryUtilLoader extends AsyncTaskLoader<ArrayList<BooksInfo>> {

    private static final String LOG_TAG = QueryUtilLoader.class.getName();

    private final String mQuery;

    public QueryUtilLoader(Context context, String query) {
        super(context);
        this.mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading is called...");

        forceLoad();
    }

    @Override
    public ArrayList<BooksInfo> loadInBackground() {

        Log.d(LOG_TAG, "loadInBackground is called...");

        if(TextUtils.isEmpty(mQuery)) {
            return null;
        }

        //noinspection UnnecessaryLocalVariable
        ArrayList<BooksInfo> books = QueryUtils.fetchEarthquakeData(mQuery);
        return books;
    }
}
