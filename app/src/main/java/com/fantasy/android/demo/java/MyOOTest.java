package com.fantasy.android.demo.java;

public class MyOOTest {

    private static class A {

        protected void print() {
            System.out.println("A");
        }

        public static void haha() {
            System.out.println("hahaAAAA");
        }
    }

    private static class B extends A{

        @Override
        protected void print() {
            System.out.println("B");
        }

        public static void haha() {
            System.out.println("hahaBBBB");
        }

        public static void xixi() {
            System.out.println("xixi");
        }
    }

    public static void main(String args[]) {

        B b = new B();

        b.print();

        ((A)b).print();

        A a = new B();
        a.print();
        //a.xixi();

        b.haha();
        ((A)b).haha();
    }
}
