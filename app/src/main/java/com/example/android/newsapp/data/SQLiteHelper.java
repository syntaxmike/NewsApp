package com.example.android.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Syntax Mike on 7/24/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String newsQuery = "CREATE TABLE " + Contract.TABLE_NEWS.TABLE_NAME + " ("+
                Contract.TABLE_NEWS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_NEWS.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_NEWS.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_URL + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_TIME + " DATE, " +
                Contract.TABLE_NEWS.COLUMN_IMAGE_URL + " TEXT" +
                "); ";

        db.execSQL(newsQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Contract.TABLE_NEWS.TABLE_NAME + " if exists;");
    }
}
