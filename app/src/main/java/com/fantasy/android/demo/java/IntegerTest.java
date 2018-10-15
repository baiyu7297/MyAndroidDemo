package com.fantasy.android.demo.java;

public class IntegerTest {

    public static void main(String args[]) {
        // -128 ~ 127
        Integer i12 = new Integer(12);
        Integer ii12 = 12;
        Integer iii12 = 12;

        System.out.println("i12 == ii12 " + (i12 == ii12));
        System.out.println("i12 == iii12 " + (ii12 == iii12));

        int i = 12;
        System.out.println("i12 == iii12 " + (ii12 == i));
        System.out.println("i12 == iii12 " + (i12 == i));

        // not -128 ~ 127
        Integer t128 = 128;
        Integer tt128 = 128;
        System.out.println("t128 == tt128 " + (tt128 == t128));
    }
}
