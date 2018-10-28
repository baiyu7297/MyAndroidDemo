package com.fantasy.android.demo.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyTest implements Callable<Integer> {

    public static void main(String args[]) {
        System.out.println("print myinterface member :" + MyInterface.mMember);

        Callable<Integer> callable = new MyTest();
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();

        try {
            System.out.println("子线程的返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " i =" + i);
        }
        return i;
    }
}
