package com.fantasy.android.demo.java;

import java.util.concurrent.TimeUnit;

public class WaitNotify{
    static boolean flag = true;//不需要为volatile，因为对于flag的操作均在synchronized锁的保护下进行，可以保证flag的内存可见性
    static Object lock = new Object();

    public static void main(String args[]) throws Exception{
        Thread waitThread = new Thread(new Wait(),"WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);//1 second  -> package java.util.cocurrent
        Thread notifyThread = new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        public void run(){
            //加锁，拥有lock的monitor
            synchronized(lock){
                //当条件不满足时，继续wait，同时释放了lock的锁
                while(flag){
                    try{
                        System.out.println("flag is ture, Wait");
                        lock.wait();
                    }catch(InterruptedException e){
                        //除了notify通知，带超时的wait()方法、线程中断机制也能唤醒此线程
                    }
                }
                System.out.println("flag is false,complete");
            }
        }
    }

    static class Notify implements Runnable{
        public void run(){
            synchronized(lock){
                //获取lock的锁，然后进行通知，通知时不会释放lock的锁，直到当前线程释放了lock，调用了notifyAll，并且WaitThread获得了锁之后，wait线程才能从wait()方法返回
                System.out.println("Notify get lock ,begin notify");
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {

                }
            }

            //synchronized(lock){
                System.out.println("Notify get lock again");
            //}
        }
    }
}
