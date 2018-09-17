package com.fantasy.android.demo.java;

public interface Animal {
    default void sounds() {
        System.out.print("Animal");
    }

    static void say() {
        System.out.print("I'm a animal");
    }
}