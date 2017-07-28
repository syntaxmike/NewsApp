package com.example.android.newsapp.utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.newsapp.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Syntax Mike on 6/22/2017.
 *
 * This class handles the network calls and parses the information into an Arraylist.
 */

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    private static final String BASE_NEWS_API =
            "https://newsapi.org/v1/articles?";

    private static final String NEXT_WEB_SOURCE = "the-next-web";

    private static final String PARAM_SORT_LATEST = "latest";

    /**
    * Insert API key in BASE_NEWS_KEY, inside quotes.
    * Hidden for security
    */

    private static final String BASE_NEWS_KEY = "Hidden";


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

    public static ArrayList<NewsItem> parseJSON(String newsItems) throws JSONException{
        ArrayList<NewsItem> allArticles = new ArrayList<>();
        JSONObject source = new JSONObject(newsItems);
        JSONArray articles = source.getJSONArray("articles");

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);;;
            NewsItem item = new NewsItem(
                    article.getString("title"),
                    article.getString("description"),
                    article.getString("url"),
                    article.getString("publishedAt"),
                    article.getString("urlToImage"),
                    article.getString("author"));
            allArticles.add(item);
        }

        return allArticles;
    }

}
