package com.fantasy.android.demo.android;

import java.io.Serializable;

/**
 * Created by fantasy on 2018/3/18.
 */

public class UserSerialize implements Serializable {

    private static final long serialVersionUID = 519039393939393L;

    static String name;
    String favorite;
    transient int age;

    @Override
    public String toString() {
        return "UserSerialize{" +
                "name='" + name + '\'' +
                ", favorite='" + favorite + '\'' +
                ", age=" + age +
                '}';
    }
}
