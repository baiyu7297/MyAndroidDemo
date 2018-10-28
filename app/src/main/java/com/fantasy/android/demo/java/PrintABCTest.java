package com.fantasy.android.demo.java;

public class PrintABCTest implements Runnable {

    private String mInput;
    private static Object mLock = new Object();
    public PrintABCTest(String input) {
        mInput = input;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (mLock) {
                mLock.notify();
                System.out.println(mInput);
                try {
                    mLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        PrintABCTest r1 = new PrintABCTest("A");
        PrintABCTest r2 = new PrintABCTest("B");

        Thread s1 = new Thread(r1);
        s1.start();
        Thread s2 = new Thread(r2);
        s2.start();
    }
}
