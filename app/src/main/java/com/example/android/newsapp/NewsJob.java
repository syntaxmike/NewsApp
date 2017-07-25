package com.example.android.newsapp;

import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobParameters;
import android.os.AsyncTask;

/**
 * Created by Syntax Mike on 7/25/2017.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                JobArticleRefresh.refreshingTheNews(NewsJob.this);
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
