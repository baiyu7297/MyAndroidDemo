package com.fantasy.android.demo.java;

public class NumberPrinter {

    private final Object printLock = new Object();

    public static void main(String args[]) {
        NumberPrinter p = new NumberPrinter();
        p.print12();
    }

    private void print12() {
        Thread a = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                synchronized (printLock) {

                    System.out.println("1");

                    printLock.notifyAll();
                    try {
                        printLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread b = new Thread(()->{
            synchronized (printLock) {
                for (int i = 0; i < 10; i++) {
                    System.out.println("2");
                    printLock.notifyAll();
                    try {
                        printLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        a.start();
        b.start();
    }
}
