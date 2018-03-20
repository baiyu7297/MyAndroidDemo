package com.fantasy.android.demo.java;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fantasy on 2018/3/11.
 */

public class EnumTest {

    public static final int TEST_ENUM_ONE = 1;
    public static final int TEST_ENUM_TWO = 2;

    @IntDef({TEST_ENUM_ONE, TEST_ENUM_TWO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TEST_ENUM_PARAMS {

    }

    private void testEnumParam(@TEST_ENUM_PARAMS int param) {
        switch (param) {
            case TEST_ENUM_ONE:
                break;
            case TEST_ENUM_TWO:
                break;
            default:
                throw new IllegalArgumentException("not in test enum exception");
        }
    }

    public static void main(String args[]) {
        EnumTest test = new EnumTest();
        test.testEnumParam(TEST_ENUM_ONE);
    }
}
