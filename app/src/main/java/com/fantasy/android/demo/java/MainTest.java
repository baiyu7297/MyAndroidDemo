package com.fantasy.android.demo.java;

import java.util.Arrays;

/**
 * Created by fantasy on 2018/3/4.
 */

public class MainTest {

    private String text = "myandroidtest";

    public static void main(String[] args) {

        String[] origin = new String[]{"1", "2", "3", "4", "5", "6"};

        String[] dest = new String[]{"a", "b", "c", "d"};


        System.arraycopy(origin, 2, dest, 1, 3);

        System.out.print("dest=" + Arrays.toString(dest));

        Example e = new Example();
        Example.InnerTest innerTest = e.getInnerTest();
        innerTest.print();
    }
}
