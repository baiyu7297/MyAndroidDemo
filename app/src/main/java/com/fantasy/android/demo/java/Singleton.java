package com.fantasy.android.demo.java;

/**
 * Created by fantasy on 2018/3/10.
 */

public class Singleton {

    private static Singleton mSingleton = new Singleton();

    public static Singleton getInstance() {
        return mSingleton;
    }
}
