package com.fantasy.android.demo.java;

import java.util.concurrent.locks.ReentrantLock;

public class LockFairTest implements Runnable {

    private ReentrantLock mReentrantLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                mReentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + " getLock!");
            } finally {
                mReentrantLock.unlock();
            }
        }
    }

    public static void main(String args[]) {
        LockFairTest lockFairRunnable = new LockFairTest();
        Thread s1 = new Thread(lockFairRunnable);
        Thread s2 = new Thread(lockFairRunnable);
        s1.start();
        s2.start();
    }
}
