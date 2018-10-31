package com.fantasy.android.demo.java;

import java.util.Arrays;

/**
 * Created by fantasy on 2018/3/4.
 */

public class MainTest {

    private String text = "myandroidtest";

    public static void main(String[] args) {

        String[] origin = new String[]{"1", "2", "3", "4", "5", "6"};

        String[] dest = new String[]{"a", "b", "c", "d"};


        System.arraycopy(origin, 2, dest, 1, 3);

        System.out.println("dest=" + Arrays.toString(dest));

        Example e = new Example();
        Example.InnerTest innerTest = e.getInnerTest();
        innerTest.print();

        int[] onezeroArray = new int[] {0,1,0,1,0,0,1,1,1,0};

        MainTest test = new MainTest();
        test.reOrderOneZeroArray(onezeroArray);
        System.out.println(Arrays.toString(onezeroArray));

        int[] array = new int[]{2,4,6,8,10};
        ListNode head = test.createList(array);
        test.printList(head);

        ListNode head2 = test.createByHeadInsert(array);
        test.printList(head2);

        ListNode head3 = test.reorderListByHeadInsert(head2);
        test.printList(head3);

        System.out.println(test.printKNodeFromTail(2, head3).value);


        int[] array1 = new int[]{2,4,6,8,10};
        int[] array2 = new int[]{1,2,3,4,5,6};
        ListNode node1 = test.createList(array1);
        ListNode node2 = test.createList(array2);

        ListNode mergedNode = test.mergeList(node1, node2);
        test.printList(mergedNode);

        int[] array3 = new int[]{2,-1,0,4,2,3,9,10};
        System.out.println("binarysearch array3=" + test.binarySearch(array3, 6));
        test.bubbleSort(array3);
        System.out.println("bubbleSort array3=" + Arrays.toString(array3));
    }

    private void reOrderOneZeroArray(int[] onezeroArray) {
        if (onezeroArray == null || onezeroArray.length == 0) return;
        int left = 0, right = onezeroArray.length - 1;
        while (left < right) {
            if (onezeroArray[left] == 0) {
                left++;
            }
            if (onezeroArray[right] == 1) {
                right--;
            }
            if (onezeroArray[left] == 1 && onezeroArray[right] == 0) {
                onezeroArray[left] = 0;
                onezeroArray[right] = 1;
                left++;
                right--;
            }
        }
    }

    private ListNode createList(int[] intArray) {
        if (intArray == null || intArray.length == 0) return null;
        ListNode head = null;
        ListNode pNow = null;
        for (int i = 0; i < intArray.length; i++) {
            if (head == null) {
                head = new ListNode();
                head.value = intArray[i];
                pNow = head;
            } else {
                ListNode node = new ListNode();
                node.value = intArray[i];
                pNow.pNext = node;
                pNow = node;
            }
        }
        return head;
    }

    private ListNode createByHeadInsert(int[] intArray) {
        if (intArray == null || intArray.length == 0) return null;
        // 空的头节点
        ListNode head = new ListNode();
        for (int i = 0; i < intArray.length; i++) {
            ListNode node = new ListNode();
            node.value = intArray[i];
            node.pNext = head.pNext;
            head.pNext = node;
        }
        return head.pNext;
    }

    private void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.pNext;
        }
        System.out.println();
    }

    private ListNode reorderListByHeadInsert(ListNode head) {
        ListNode pHead = new ListNode();
        while (head != null) {
            ListNode memo = head.pNext;
            head.pNext = pHead.pNext;
            pHead.pNext = head;
            head = memo;
        }
        return pHead.pNext;
    }

    private ListNode printKNodeFromTail(int k, ListNode head) {
        if (head == null) return null;
        ListNode pNow = head;
        while(pNow != null && k-- > 0) {
            pNow = pNow.pNext;
        }
        if (k > 0) return null;
        ListNode kNow = head;
        while(pNow != null) {
            pNow = pNow.pNext;
            kNow = kNow.pNext;
        }
        return kNow;
    }

    private ListNode findEntranceForCircleList(ListNode head) {
        if (head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        do {
            fast = fast.pNext.pNext;
            slow = slow.pNext;
        } while (head != slow);

        fast = head;
        while (fast != slow) {
            fast = fast.pNext;
            slow = slow.pNext;
        }

        return slow;
    }

    private ListNode mergeList(ListNode head1, ListNode head2) {
        ListNode head = new ListNode();
        ListNode pNow = head;
        while (head1 != null && head2 != null) {
            if (head1.value <= head2.value) {
                pNow.pNext = head1;
                head1 = head1.pNext;
            } else {
                pNow.pNext = head2;
                head2 = head2.pNext;
            }
            pNow = pNow.pNext;
        }

        if (head2 != null) {
            pNow.pNext = head2;
        }
        if (head1 != null) {
            pNow.pNext = head1;
        }
        return head.pNext;
    }

    private static class ListNode {
        int value;
        ListNode pNext;
    }

    // 如果有符合条件的数字，则它出现的次数比其他所有数字出现的次数和还要多。
    // 在遍历数组时保存两个值：一是数组中一个数字，一是次数。遍历下一个数字时，
    // 若它与之前保存的数字相同，则次数加1，否则次数减1；
    // 若次数为0，则保存下一个数字，并将次数置为1。
    // 遍历结束后，所保存的数字即为所求。然后再判断它是否符合条件即可。
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array == null || array.length == 0) return 0;
        int result = array[0];
        int times = 0;
        for (int i = 0; i < array.length; i++) {
            if (times == 0) {
                result = array[i];
                times++;
            }
            if (result == array[i]) {
                times++;
            } else {
                times--;
            }
        }

        times = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == result) {
                times++;
            }
        }

        return (times > array.length /2 ) ? result : 0;
    }

    private int binarySearch(int[] array, int k) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length - 1;
        int mid;
        while (begin <= end) {
            mid = (begin + end) / 2;
            if (array[mid] > k) {
                end = mid - 1;
            } else if (array[mid] < k) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private void bubbleSort(int[] array) {
        if (array == null || array.length == 0) return;
        for (int i = 0; i < array.length - 1; i++) {
            int sort = 0;
            for (int j = 0; j < array.length -1 - i; j++) {
                if (array[j] > array[j+1]) {
                    sort++;
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
            if (sort == 0) {
                break;
            }
        }
    }

}
