package com.example.android.newsapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.newsapp.data.DBHelper;
import com.example.android.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Syntax Mike on 7/25/2017.
 */

public class ArticleRefresh {

    public static void refreshingTheNews(Context context){
        ArrayList<NewsItem> updatedArticles;
        URL newsSourceUrl = NetworkUtils.makeURL();

        SQLiteDatabase mNewsDatabase = new DBHelper(context).getWritableDatabase();

        try {

            DBHelper.deleteAll(mNewsDatabase);
            String jsonNews = NetworkUtils.getResponseFromHttpUrl(newsSourceUrl);
            updatedArticles = NetworkUtils.parseJSON(jsonNews);
            DBHelper.bulkInsert(mNewsDatabase, updatedArticles);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mNewsDatabase.close();
    }
}
