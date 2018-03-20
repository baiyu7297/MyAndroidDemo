package com.fantasy.android.demo.java;

/**
 * Created by fantasy on 2018/3/10.
 */

public class Singleton2 {

/*
    //双重加锁
    private static volatile Singleton2 singleton = null;

    public static Singleton2 getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                singleton = new Singleton2();
            }
        }
        return singleton;
    }
*/

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用时才会装载，从而实现了延迟加载
     * @author dream
     *
     */
    private static class SingletonHolder {
        private static final Singleton2 instance = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return SingletonHolder.instance;
    }
}
