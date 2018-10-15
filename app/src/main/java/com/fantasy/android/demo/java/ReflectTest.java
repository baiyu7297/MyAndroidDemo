package com.fantasy.android.demo.java;

import java.lang.reflect.Method;

public class ReflectTest {

    public static void main(String args[]) {
        try {
            Class clazz = Class.forName("com.fantasy.android.demo.java.ReflectType");

            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                System.out.println("method: " + m);
            }

            Object reflectType = clazz.newInstance();
            Method method = clazz.getMethod("print", null);
            method.invoke(reflectType, null);

            Method method2 = clazz.getDeclaredMethod("print2", String.class);
            method2.setAccessible(true);
            method2.invoke(reflectType, "hahahahaha");

            Method method3 = clazz.getDeclaredMethod("print3", null);
            method3.setAccessible(true);
            method3.invoke(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
