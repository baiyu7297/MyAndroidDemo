package com.fantasy.android.demo.java;

import java.util.Arrays;

public class LamdaPerson implements Animal{

    public String name;
    public int age;

    public LamdaPerson(String n, int a) {
        name = n;
        age = a;
    }

    public static void say() {
        System.out.print("I'm a person");
    }

    public void laugh() {
        System.out.print("laughing...");
    }

    public static class CompareProvider {
        public int compareByAge(LamdaPerson a, LamdaPerson b) {
            return a.age-(b.age);
        };
    }

    public static void main(String args[]) {
        LamdaPerson personArray[] = new LamdaPerson[3];
        personArray[0] = new LamdaPerson("wang", 31);
        personArray[1] = new LamdaPerson("baobao", 2);
        personArray[2] = new LamdaPerson("mama", 18);

        /*Arrays.sort(personArray, new Comparator<LamdaPerson>() {
            @Override
            public int compare(LamdaPerson lamdaPerson, LamdaPerson t1) {
                return lamdaPerson.age-t1.age;
            }
        });*/

        //Arrays.sort(personArray, (person1, person2) -> compareByAge(person1, person2));

        Arrays.sort(personArray, new CompareProvider()::compareByAge);

        for (LamdaPerson p : personArray) {
            System.out.print(p.name + " ");
            p.sounds();
            System.out.println();
            LamdaPerson.say();
            System.out.println();
            Animal.say();
            System.out.println();
        }
    }


    @Override
    public void sounds() {
        Animal.super.sounds();
    }
}
