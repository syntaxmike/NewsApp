package com.example.android.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.example.android.newsapp.data.Contract;
import com.example.android.newsapp.data.DBHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{
    private ProgressBar mProgress;
    private RecyclerView mRV;
    private Cursor mCursor;
    private NewsAdapter mNewsAdapter;
    private SQLiteDatabase mNewsDatabase;

    private static final int NEWS_LOADER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mRV = (RecyclerView)findViewById(R.id.recyclerView);
        mRV.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            newsLoader();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        NewsJobDispatch.refresh(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNewsDatabase = new DBHelper(MainActivity.this).getReadableDatabase();
        mCursor = DBHelper.getAll(mNewsDatabase);
        mNewsAdapter = new NewsAdapter(mCursor, this);
        mRV.setAdapter(mNewsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNewsDatabase.close();
        mCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if(itemNumber == R.id.search){
            Toast.makeText(this, "Searching...", Toast.LENGTH_LONG).show();
            newsLoader();

            //Execute ASyncTask

//            NetworkTask task = new NetworkTask();
//            task.execute();
        }

        return true;
    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        mCursor.moveToPosition(clickedItemIndex);
        String url = mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_URL));

        Uri newsPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, newsPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                JobArticleRefresh.refreshingTheNews(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        mProgress.setVisibility(View.GONE);
        mNewsDatabase = new DBHelper(MainActivity.this).getReadableDatabase();
        mCursor = DBHelper.getAll(mNewsDatabase);

        mNewsAdapter = new NewsAdapter(mCursor, this);
        mRV.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }



    public void newsLoader() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }
}
