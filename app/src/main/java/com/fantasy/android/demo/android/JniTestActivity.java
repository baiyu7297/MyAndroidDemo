package com.fantasy.android.demo.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class JniTestActivity extends Activity {

    static {
        System.loadLibrary("myLib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getHelloWorld(), Toast.LENGTH_SHORT).show();
    }

    public native String getHelloWorld();
}