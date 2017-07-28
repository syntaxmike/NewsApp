package com.example.android.newsapp.dispatcher;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Syntax Mike on 7/25/2017.
 */

/**
 * This class is intended for the Firebase Job Dispatcher initialization.
 */

public class NewsJobDispatch {
    private static final int INTERVAL_MINUTES = 59;
    private static final int SYNC_SECONDS = 2;


    /**
     * Boolean checks if Job was already scheduled.
     */
    private static boolean mInitial;


    synchronized public static void refresh(@NonNull final Context context){
        if (mInitial) return;

        Driver mDriver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(mDriver);


        /**
         * Builds the Job using Firebase Dispatcher
         * Purpose of the Job is to refresh the database every X amount of time.
         */
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag("NewsDispatch")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(INTERVAL_MINUTES,
                        INTERVAL_MINUTES + SYNC_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        /**
         * Set true to prevent another Job being initiated
         */
        mInitial = true;
    }
}
