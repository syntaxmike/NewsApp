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

public class NewsJobDispatch {
    private static final int INTERVAL_MINUTES = 30;
    private static final int SYNC_SECONDS = 30;


    private static boolean mInitial;

    synchronized public static void refresh(@NonNull final Context context){
        if (mInitial) return;

        Driver mDriver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(mDriver);

        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag("DispatcherService")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(INTERVAL_MINUTES,
                        INTERVAL_MINUTES + SYNC_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        mInitial = true;
    }
}
