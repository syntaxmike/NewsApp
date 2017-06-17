package com.example.android.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String TAG = "NetworkUtils";

    public static final String BASE_NEWS_API =
            "https://newsapi.org/v1/articles?";

    public static final String NEXT_WEB_SOURCE = "the-next-web";

    public static final String PARAM_SORT_LATEST = "latest";

    public static final String BASE_NEWS_KEY = "26db1a84c65944c5b919ecb488383298";


    public static URL makeURL(){
        Uri uri = Uri.parse(BASE_NEWS_API).buildUpon()
                .appendQueryParameter("source", NEXT_WEB_SOURCE)
                .appendQueryParameter("sortBy", PARAM_SORT_LATEST)
                .appendQueryParameter("apiKey", BASE_NEWS_KEY).build();

        URL url = null;

        try {
            String urlString = uri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return(input.hasNext())? input.next():null;

        } finally {
            urlConnection.disconnect();
        }
    }

}
