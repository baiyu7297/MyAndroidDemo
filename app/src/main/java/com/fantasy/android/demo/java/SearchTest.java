package com.fantasy.android.demo.java;

import java.util.Arrays;

public class SearchTest {

    public static void main(String args[]) {
        int[] array = new int[]{222,99,3,4,66,77,88,222,75,22,0,111,333};
        SearchTest test = new SearchTest();
        int result = test.binarySearch(array, 1112);
        System.out.println(result);

        test.bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }

    // 二分查找
    private int binarySearch(int[] intArray, int searchKey) {

        if (intArray == null || intArray.length <= 0) return -1;
        int low = 0;
        int high = intArray.length - 1;
        int middle, value;
        while (low <= high) {
            middle = (low + high) / 2;
            value = intArray[middle];
            if (intArray[middle] == searchKey) {
                return middle;
            } else if (value < searchKey){
                low = middle + 1;
            } else if (value > searchKey) {
                high = middle - 1;
            }
        }
        return -1;
    }

    // 冒泡排序
    private void bubbleSort(int[] intArray) {
        if (intArray == null || intArray.length <= 0) return;
        int temp = 0;
        for (int i = 0; i < intArray.length - 1; i++) {
            for (int j = 0; j < intArray.length - 1 - i; j++) {
                if (intArray[j] > intArray[j + 1]) {
                    temp = intArray[j];
                    intArray[j] = intArray[j + 1];
                    intArray[j + 1] = temp;
                }
            }
        }
    }


}
