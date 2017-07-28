package com.example.android.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by Syntax Mike on 7/24/2017.
 *
 * This class contains the constants for database.
 */

public class Contract {


    /**
     * Constants for database table.
     */
    public static class TABLE_NEWS implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_IMAGE_URL = "imageURL";
    }
}
