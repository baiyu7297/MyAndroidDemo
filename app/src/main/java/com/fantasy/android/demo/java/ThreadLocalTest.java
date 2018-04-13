package com.fantasy.android.demo.java;

import android.util.Log;

public class ThreadLocalTest {

    private static final String TAG = "ThreadLocalTest";
    private static ThreadLocal<Boolean> mMyThreadLocal = new ThreadLocal<Boolean>();

    public static void main(String args[]) {

        mMyThreadLocal.set(true);
        System.out.print("main thread=" + mMyThreadLocal.get());

        new Thread("thread_one") {
            @Override
            public void run() {
                mMyThreadLocal.set(false);
                System.out.print("thread_one=" + mMyThreadLocal.get());
            }
        }.start();

        new Thread("thread_two") {
            @Override
            public void run() {
                mMyThreadLocal.set(true);
                System.out.print("thread_two=" + mMyThreadLocal.get());
            }
        }.start();
    }

}
