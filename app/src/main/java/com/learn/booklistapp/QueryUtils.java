package com.learn.booklistapp;

import android.text.TextUtils;
import android.util.Log;

import com.learn.Models.BooksInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    public static ArrayList<BooksInfo> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);         //makeHttpRequest is taking url object
            Log.i(LOG_TAG, "JsonResponse had been taken by httpReq");
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<BooksInfo> booksInfos = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return booksInfos;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.i(LOG_TAG, "Successfully Url object created");
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i(LOG_TAG, "Http Request Successfully initiated");
            } else {
                Log.i(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.i(LOG_TAG, "Reading from Stream");
        return output.toString();
    }

    private static ArrayList<BooksInfo> extractFeatureFromJson(String booksJsonResponse) {
        // If the JSON string is empty or null, then return early.
        ArrayList<BooksInfo> booksInfos = new ArrayList<>();
        if (TextUtils.isEmpty(booksJsonResponse)) {
            return booksInfos;
        }

        try {
            JSONObject baseJasonObject = new JSONObject(booksJsonResponse);
            JSONArray booksArray = null;
            if (baseJasonObject.has("items")) {
                booksArray = baseJasonObject.getJSONArray("items");
            }

            if (booksArray != null){
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject currentBook = booksArray.getJSONObject(i);
                    JSONObject volInfo = currentBook.getJSONObject("volumeInfo");

                    String tittle = "No title";
                    if (volInfo.has("title"))
                        tittle = volInfo.getString("title");

                    String publisher = "No Info";
                    if (volInfo.has("publisher"))
                        publisher = volInfo.getString("publisher");

                    ArrayList<String> authorList = new ArrayList<>();
                    if (volInfo.has("authors")) {
                        JSONArray authors = volInfo.getJSONArray("authors");
                        for (int j = 0; j < authors.length(); j++) {
                            authorList.add(authors.getString(j));
                        }
                    }


                    String publishingDate = "No info";
                    if (volInfo.has("publishedDate"))
                        publishingDate = volInfo.getString("publishedDate");

                    String description = "No info";
                    if (volInfo.has("description"))
                        description = volInfo.getString("description");

                    int pageCount = -1;
                    if (volInfo.has("pageCount"))
                        pageCount = volInfo.getInt("pageCount");

                    String imageLink = null;
                    if (volInfo.has("imageLinks")) {
                        JSONObject thumbnail = volInfo.getJSONObject("imageLinks");
                        imageLink = thumbnail.getString("thumbnail");
                    }

                    String languageCode = "No info";
                    String languageName = "No info";
                    if (volInfo.has("language")) {
                        languageCode = volInfo.getString("language");
                        Locale loc = new Locale(languageCode);
                        languageName = loc.getDisplayLanguage(loc);
                    }

                    String previewLink = null;
                    if (volInfo.has("previewLink"))
                        previewLink = volInfo.getString("previewLink");

                    double rating = 0;
                    if (volInfo.has("averageRating"))
                        rating = volInfo.getDouble("averageRating");

                    int ratingCount = 0;
                    if (volInfo.has("ratingsCount"))
                        ratingCount = volInfo.getInt("ratingsCount");

                    JSONObject buy = currentBook.getJSONObject("saleInfo");

                    String buyingLink = null;
                    if (buy.has("buyLink"))
                        buyingLink = buy.getString("buyLink");

                    BooksInfo booksInfo = new BooksInfo();
                    booksInfo.setAuthors(authorList);
                    booksInfo.setBookTitle(tittle);
                    booksInfo.setDescription(description);
                    booksInfo.setBuyingLink(buyingLink);
                    booksInfo.setLanguage(languageName);
                    booksInfo.setPageCount(pageCount);
                    booksInfo.setRating(rating);
                    booksInfo.setRatingCount(ratingCount);
                    booksInfo.setThumbnailLink(imageLink);
                    booksInfo.setPreviewLink(previewLink);
                    booksInfo.setPublisher(publisher);
                    booksInfo.setPublishingDate(publishingDate);

                    booksInfos.add(booksInfo);

                }
        }
            Log.i(LOG_TAG, "Extracting Json Features");
            return booksInfos;
        } catch (JSONException e) {
            Log.i(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return booksInfos;

    }
}