package com.fantasy.android.demo.java;

import android.util.Log;

/**
 * Created by fantasy on 2018/3/15.
 */

public class ArrayTest {

    public static void main(String args[]) {
        String a = "a";
        String b = "b";
        String c = "c";
        Object[] res = new String[]{a, b, c};
        Object[] res2 = res;


        for (int i = 0; i < res.length; i++) {
            System.out.println("res[" + i + "]=" + res[i]);
            System.out.println("res2[" + i + "]=" + res2[i]);
        }
    }
}
