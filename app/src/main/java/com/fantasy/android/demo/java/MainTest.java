package com.fantasy.android.demo.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by fantasy on 2018/3/4.
 */

public class MainTest {

    private String text = "myandroidtest";

    public static void main(String[] args) {

/*        String[] origin = new String[]{"1", "2", "3", "4", "5", "6"};

        String[] dest = new String[]{"a", "b", "c", "d"};


        System.arraycopy(origin, 2, dest, 1, 3);

        System.out.print("dest=" + Arrays.toString(dest));

        Example e = new Example();
        Example.InnerTest innerTest = e.getInnerTest();
        innerTest.print();*/

        MainTest test = new MainTest();

        int[] nums = new int[] {0, 1, 2, 3, 4, 4, 6, 7,8, 9};
        int[] duplication = new int[1];
        if (test.duplicate(nums, nums.length, duplication)) {
            System.out.println("duplicate==" + duplication[0]);
        }

        test.printListNode(test.reverseListNode(test.createListByHeadInsert(nums)));

        ListNode node1 = new ListNode();
        ListNode node2 = new ListNode();
        ListNode node3 = new ListNode();
        node1.value = 11;
        node2.value = 11;
        node3.value = 33;
        node1.pNext = node2;
        node2.pNext = node3;

        System.out.println();
        test.printListNode(node1);
        //test.removeFromList(node1, node3);
        System.out.println();
        test.printListNode(node1);

        //System.out.print("minuminarray=" + test.findMinmumInArray(new int[] {1,1,1,1,1,0,1,1}));
        System.out.println();
        test.printListNode(test.deleteDuplicationInList(test.createListByHeadInsert(nums)));

        int[] onezeroArray = new int[] {0,1,0,1,0,0,1,1,0,1,1};
        test.reorderOneZeroArray(onezeroArray);
        System.out.println("onzero=" + Arrays.toString(onezeroArray));


    }

    private boolean duplicate(int[] nums, int length, int[] duplication) {
        if (nums == null || length <= 0) return false;
        for (int i = 0; i < length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    duplication[0] = nums[i];
                    return true;
                }
                swap(nums, i, nums[i]);
            }
        }
        return false;
    }

    private void swap(int[] num, int i, int j) {
        int t = num[i];
        num[i] = num[j];
        num[j] = t;
    }


    private ListNode createListByHeadInsert(int[] input) {
        ListNode head = new ListNode();
        for (int i = 0; i < input.length; i++) {
            ListNode newNode = new ListNode();
            newNode.value = input[i];
            // 插到head的下一个
            newNode.pNext = head.pNext;
            // head链接当前
            head.pNext = newNode;
        }

        return head.pNext;
    }

    private void printListNode(ListNode node) {
        while (node != null) {
            System.out.print(node.value + "-");
            node = node.pNext;
        }
    }

    private ListNode reverseListNode(ListNode listNode) {
        if (listNode == null) return null;
        if (listNode.pNext == null) return listNode;

        // 空的头
        ListNode head = new ListNode();
        while (listNode != null) {
            // 保存当前的next
            ListNode memo = listNode.pNext;
            // 当前插到头的next
            listNode.pNext = head.pNext;
            // 头的next指向当前
            head.pNext = listNode;
            // 当前的移位
            listNode = memo;
        }

        return head.pNext;
    }

    // 删除链表中结点
    private void removeFromList(ListNode head, ListNode toDelete) {
        if (head == null || toDelete == null) return;
        // 不是尾结点
        if (toDelete.pNext != null) {
            toDelete.value = toDelete.pNext.value;
            toDelete.pNext = toDelete.pNext.pNext;
        } else {
            ListNode pPrev = head;
            while (pPrev.pNext != toDelete) {
                pPrev = pPrev.pNext;
            }
            pPrev.pNext  = null;
        }
    }

    // 旋转数组找最小数{4,5,0,1,2,3}
    private int findMinmumInArray(int [] nums) {
        if (nums == null || nums.length == 0) return -1;
        int l = 0, h = nums.length -1;
        while (l < h) {
            int m = (l + h) /2;
            if (nums[l] == nums[m] && nums[m] == nums[h]) {
                return minInArray(nums, l, h);
            } else if (nums[l] <= nums[m]) {
                l = m + 1;
            } else {
                h = m;
            }
        }
        return nums[l];
    }

    private int minInArray(int[] nums, int l, int h) {
        for (int i = l; i <= h; i++) {
            if (nums[i] > nums[i + 1])
                return nums[i + 1];
        }
        return nums[l];
    }


    // 删除有序链表中的重复结点
    private ListNode deleteDuplicationInList(ListNode pHead) {
        if (pHead == null || pHead.pNext == null) return pHead;

        if (pHead.value == pHead.pNext.value) {
            ListNode next = pHead.pNext;
            while (next != null && next.value == pHead.value) {
                next = next.pNext;
            }
            return deleteDuplicationInList(next);
        } else {
            pHead.pNext = deleteDuplicationInList(pHead.pNext);
            return pHead;
        }
    }

    // 0, 1数组 排序
    private void reorderOneZeroArray(int[] onezeroArray) {
        if (onezeroArray == null || onezeroArray.length <= 0) return;

        int left = 0;
        int right = onezeroArray.length - 1;
        while (left < right) {
            if (onezeroArray[left] == 0) {
                left++;

            }
            if (onezeroArray[right] == 1) {
                right--;

            }
            if (onezeroArray[left] == 1 && onezeroArray[right] == 0) {
                int temp = onezeroArray[left];
                onezeroArray[left] = onezeroArray[right];
                onezeroArray[right] = temp;
                left++;
                right--;
            }
        }
    }


    private int getFirstK(int[] array, int k) {
        int begin = 0;
        int end = array.length - 1;
        while (begin <= end) {
            int mid = (begin + end) / 2;
            if (array[mid] > k) {
                end = mid - 1;
            } else if (array[mid] < k) {
                begin = mid + 1;
            } else {
                if (mid == 0 || (mid > 0 && array[mid-1] != k)) {
                    return mid;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

}
