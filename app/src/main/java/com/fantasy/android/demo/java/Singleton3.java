package com.fantasy.android.demo.java;

public class Singleton3 {

/*    private static final Singleton3 mInstance = new Singleton3();

    public Singleton3 getInstance() {
        return mInstance;
    }*/

/*    private static volatile Singleton3 mInstance;

    public Singleton3 fgetInstance() {
        if (mInstance == null) {
            synchronized (Singleton3.this) {
                mInstance = new Singleton3();
            }
        }
        return mInstance;
    }*/

    private Singleton3() {

    }

    public static class SingletonFactory {
        private static final Singleton3 mInstance = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return SingletonFactory.mInstance;
    }
}
