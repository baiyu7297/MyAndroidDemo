package com.fantasy.android.demo.java;

public class ConsumerProducer {

    static class Consumer extends Thread{

        private Box mBox;

        public Consumer(Box box) {
            mBox = box;
        }

        @Override
        public void run() {
            synchronized (mBox) {
                for (int i=1; i<5;i++) {
                    while (mBox.boxValue == 0) {
                        try {
                            System.out.println(getName() + "空了");
                            mBox.wait();
                        } catch (InterruptedException e) {

                        }
                    }
                    System.out.println(getName() + "消费了 boxvalue=" + mBox.boxValue);
                    mBox.boxValue = 0;
                    mBox.notify();
                }
            }
        }
    }

    static class Producer extends Thread {

        private Box mBox;

        public Producer(Box box) {
            mBox = box;
        }
        @Override
        public void run() {
            synchronized (mBox) {
                for (int i=1; i<5;i++) {
                    while (mBox.boxValue != 0) {
                        try {
                            System.out.println(getName() + "满了");
                            mBox.wait();
                        } catch (InterruptedException e) {

                        }
                    }
                    mBox.boxValue = i;
                    System.out.println(getName() + "生产了 boxvalue=" + i);
                    mBox.notify();
                }
            }
        }
    }

    static class Box {
        int boxValue = 0;
    }

    public static void main(String args[]) {
        final Box box = new Box();
        Consumer c = new Consumer(box);
        Producer p = new Producer(box);
        p.start();
        c.start();
    }
}
