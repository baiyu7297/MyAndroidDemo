package com.fantasy.android.demo.android;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by fantasy on 2018/3/10.
 */

public class MyApplication extends Application{

    private static final String TAG = "WANG_DEMO";

    public int test = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext->" + this);
    }
}
