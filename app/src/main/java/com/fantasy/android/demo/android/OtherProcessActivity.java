package com.fantasy.android.demo.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fantasy.android.demo.MyApplication;

/**
 * Created by fantasy on 2018/3/10.
 */

public class OtherProcessActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.frame_layout_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WANG", "application test22=" + ((MyApplication)getApplication()).test);
    }
}
