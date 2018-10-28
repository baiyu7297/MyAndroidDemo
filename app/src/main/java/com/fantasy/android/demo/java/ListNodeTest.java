package com.fantasy.android.demo.java;

import java.util.Stack;

public class ListNodeTest {

    public static void main(String args[]) {

        ListNodeTest test = new ListNodeTest();

        ListNode node1 = new ListNode();
        node1.value = 11;
        ListNode node2 = new ListNode();
        node2.value = 22;
        ListNode node3 = new ListNode();
        node3.value = 33;
/*        node1.pNext = node2;
        node2.pNext = node3;*/

/*        test.printListNode(node1);

        test.addToTail(node1, 9999);


        test.printListNode(node1);
        System.out.println();
        test.printFromTail(node1);*/

        ListNode pReverse = test.reverseList(node1);
        test.printListNode(pReverse);

    }

    // 链表尾部插入
    private void addToTail(ListNode pHead, int value) {

        ListNode pNew = new ListNode();
        pNew.value = value;
        pNew.pNext = null;

        if (pHead == null) pHead = pNew;
        ListNode pNow = pHead;
        while (pNow.pNext != null) {
            pNow = pNow.pNext;
        }

        pNow.pNext = pNew;
    }

    // 正向打印链表
    private void printListNode(ListNode pHead) {
        if (pHead == null) return;

        System.out.println();
        ListNode pNow = pHead;

        while (pNow != null) {
            System.out.print(pNow.value + "->");
            pNow = pNow.pNext;
        }
    }

    // 反向打印链表
    private void printFromTail(ListNode pHead) {
        if (pHead == null) return;

        System.out.println();
        ListNode pNow = pHead;
        Stack<Integer> myStack = new Stack<>();

        while (pNow != null) {
            myStack.push(pNow.value);
            pNow = pNow.pNext;
        }

        while (!myStack.isEmpty()) {
            System.out.print(myStack.pop() + "<-");
        }
    }

    // 链表反转
    private ListNode reverseList(ListNode pHead) {
        if (pHead == null) return null;
        ListNode pPrev = null;
        ListNode pNow = pHead;
        ListNode pReversed = pHead;
        while (pNow != null) {
            ListNode pNext = pNow.pNext;
            if (pNext == null) pReversed = pNow;
            pNow.pNext = pPrev;
            pPrev = pNow;
            pNow = pNext;
        }

        return pReversed;
    }
}
