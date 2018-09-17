package com.fantasy.android.demo.java;

public class LambdaTest {

    public static void main(String args[]) {
        LambdaTest test = new LambdaTest();

        MathOperation add = (int a, int b) -> a + b;

        MathOperation2 minus = c -> {
            System.out.println("minus");
            return 0 - c;
        };

        System.out.println(add.operate(1111, 2222));
        System.out.println(minus.minus(999));

        test.say();
    }

    private void say() {
        String word = "hahaha";
        SaySth saySth = words -> {
            //word = "xxxxxxx";
            System.out.print(words + word);
        };
        saySth.say("say");
    }

    interface MathOperation {
        int operate(int a, int b);
        //int operate2(int b, int c);
    }

    interface MathOperation2 {
        int minus(int c);
    }

    interface SaySth {
        void say(String words);
    }
}
