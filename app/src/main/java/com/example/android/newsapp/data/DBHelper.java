package com.example.android.newsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Syntax Mike on 7/24/2017.
 *
 * This class creates and handles the database data.
 */



public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "THE_NEWS_DB_MO.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Creates the database and inserts the appropriate columns.
         */
        String newsQuery = "CREATE TABLE " + Contract.TABLE_NEWS.TABLE_NAME + " ("+
                Contract.TABLE_NEWS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_NEWS.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_NEWS.COLUMN_AUTHOR + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_URL + " TEXT, " +
                Contract.TABLE_NEWS.COLUMN_TIME + " DATE, " +
                Contract.TABLE_NEWS.COLUMN_IMAGE_URL + " TEXT" +
                "); ";
        db.execSQL(newsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * If Database version do not match drop existing table.
         */
        db.execSQL("DROP TABLE " + Contract.TABLE_NEWS.TABLE_NAME + " IF EXISTS;");
    }



    /**
     * Helper method uses cursor for the database and sorts by date/time.
     */
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                Contract.TABLE_NEWS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.TABLE_NEWS.COLUMN_TIME + " DESC"
        );
        return cursor;
    }

    /**
     * Helper method that inserts data into the database using transaction to prevent multiple open/close.
     */
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles) {

        db.beginTransaction();
        try {
            for (NewsItem a : articles) {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_NEWS.COLUMN_TITLE , a.getTitle());
                cv.put(Contract.TABLE_NEWS.COLUMN_DESCRIPTION, a.getDescription());
                cv.put(Contract.TABLE_NEWS.COLUMN_AUTHOR, a.getAuthor());
                cv.put(Contract.TABLE_NEWS.COLUMN_URL, a.getUrl());
                cv.put(Contract.TABLE_NEWS.COLUMN_TIME, a.getTime());
                cv.put(Contract.TABLE_NEWS.COLUMN_IMAGE_URL, a.getImageURL());
                db.insert(Contract.TABLE_NEWS.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Helper method deletes entire data from database in preparation of a refresh.
     */
    public static void deleteAll(SQLiteDatabase db) {
        db.delete(Contract.TABLE_NEWS.TABLE_NAME, null, null);
    }

}

