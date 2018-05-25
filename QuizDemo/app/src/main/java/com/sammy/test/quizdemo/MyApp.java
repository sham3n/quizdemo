package com.sammy.test.quizdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by sng on 4/9/18.
 */

public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
