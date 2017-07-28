package com.example.android.newsapp.dispatcher;

import com.example.android.newsapp.data.DBRefresh;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Syntax Mike on 7/25/2017.
 *
 * This class handles the intended Job method for refreshing the database.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    /**
     * At the start of the Job an AsyncTask is created to handle changes in the
     * background threads and execute once done. Refreshes the database of news articles.
     */
    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                DBRefresh.refreshingTheNews(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);

            }
        };


        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {

        if (mBackgroundTask != null) mBackgroundTask.cancel(false);

        return true;
    }



}
