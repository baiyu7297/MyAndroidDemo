package com.fantasy.android.demo.java;

import android.support.annotation.NonNull;

import java.util.concurrent.locks.ReentrantLock;

public class SellTickTest {

    public static void main(String args[]) {
        SellThread s1 = new SellThread("s1");
        SellThread s2 = new SellThread("s2");
        SellThread s3 = new SellThread("s3");
        SellThread s4 = new SellThread("s4");
        s1.start();
        s2.start();
        s3.start();
        s4.start();
    }



    private static class SellThread extends Thread {

        public SellThread(@NonNull String name) {
            super(name);
        }

        static int ticketNumber = 100;
        static Object mLock = new Object();

        //static ReentrantLock mLock = new ReentrantLock();

        private void sellTicket() {
            synchronized (mLock) {

                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //mLock.lock();
                    synchronized (mLock) {
                        if (ticketNumber >= 0) {
                            System.out.println(Thread.currentThread().getName() + " || ticket left =" + ticketNumber--);
                        }
                    }
                    //mLock.unlock();
                }

            }
        }

        @Override
        public void run() {
            sellTicket();
        }
    }
}
