package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.example.android.newsapp.RepoItems.NewsRepositoryItems;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String mTAG = "MainActivity";
    private ProgressBar mProgress;
    private RecyclerView mRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mRV = (RecyclerView)findViewById(R.id.recyclerView);

        mRV.setLayoutManager(new LinearLayoutManager(this));
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

            //Execute ASyncTask

            NetworkTask task = new NetworkTask();
            task.execute();
        }

        return true;
    }

    class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsRepositoryItems>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsRepositoryItems> doInBackground(URL... params) {
            ArrayList<NewsRepositoryItems> allArticles = null;

            URL newsSourceUrl = NetworkUtils.makeURL();

            Log.d(mTAG, "url: " + newsSourceUrl.toString());
            try {

                String newsJson = NetworkUtils.getResponseFromHttpUrl(newsSourceUrl);
                allArticles = NetworkUtils.parseJSON(newsJson);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return allArticles;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsRepositoryItems> articles) {
            super.onPostExecute(articles);
            mProgress.setVisibility(View.GONE);
            if (articles != null) {
                NewsAdapter newsAdapter = new NewsAdapter(articles, new NewsAdapter.ItemClickListener() {

                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = articles.get(clickedItemIndex).getUrl();
                        Log.d(mTAG, String.format("Url %s", url));
                        openWebPage(url);
                    }
                });
                mRV.setAdapter(newsAdapter);
            }
        }

        public void openWebPage(String url) {
            Uri newsPage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, newsPage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
