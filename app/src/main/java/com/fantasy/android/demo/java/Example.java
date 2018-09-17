package com.fantasy.android.demo.java;

/**
 * Created by fantasy on 2018/3/7.
 */

public class Example {

    public String text = "Example";

    public InnerTest getInnerTest() {
        return new InnerTest();
    }

    public class InnerTest {
        public void print() {
            System.out.print(text);
        }
    }

    public void test() {
        System.out.print("Example test");
    }

    public static void staticTest() {
        System.out.print("Example staticTest");
    }
}
