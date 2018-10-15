package com.fantasy.android.demo.java;


public class ReflectType {

    private String member;

    static {
        System.out.println("this is refect type static block");
    }

    public void print() {
        System.out.println("this is refect type printing");
    }

    private void print2(String somthing) {
        System.out.println(somthing);
    }

    private static void print3() {
        System.out.println("print3---->");
    }
}
