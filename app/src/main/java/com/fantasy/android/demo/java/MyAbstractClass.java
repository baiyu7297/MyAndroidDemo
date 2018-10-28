package com.fantasy.android.demo.java;

public abstract class MyAbstractClass {

    private int mMember;

    public abstract void say();

    public abstract void doSomething();

    public MyAbstractClass() {
        System.out.print("MyAbstractClass constructor");
    }
}
