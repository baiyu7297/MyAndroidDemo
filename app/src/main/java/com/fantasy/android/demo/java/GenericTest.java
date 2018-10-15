package com.fantasy.android.demo.java;

import java.util.ArrayList;

public class GenericTest {

    public static void main(String args[]) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaaaa");
        ///list.add(1111);

        for (Object o : list) {
            String aaaa = (String) o;
            System.out.println(o);
        }

        GenericClass<String> gs = new GenericClass<>();
        gs.memberT = "hahaa";

    }

    private static class GenericClass<T> {

        public T memberT;

        public void sayT(T t) {
            System.out.println(t);
        }
    }
}
