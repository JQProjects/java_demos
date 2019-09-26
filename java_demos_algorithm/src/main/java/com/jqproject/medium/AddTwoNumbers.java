package com.jqproject.medium;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 姜庆
 * @create 2019-09-26 14:59
 * @desc 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。  您可以假设除了数字 0 之外，这两个数都不会以 0 开头
 **/
public class AddTwoNumbers {

    /**
     * 利用BigDecimal进行处理大数据相加
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        List<Integer> list1 = new ArrayList<>();
        while(l1.next!=null){
            list1.add(l1.val);
            l1 = l1.next;
        }
        list1.add(l1.val);

        List<Integer> list2 = new ArrayList<>();
        while(l2.next!=null){
            list2.add(l2.val);
            l2 = l2.next;
        }
        list2.add(l2.val);

        Collections.reverse(list1);
        Collections.reverse(list2);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list1.size(); i++) {
            sb.append(list1.get(i));
        }
        BigInteger int1 = new BigInteger(sb.toString());
        sb.delete(0,list1.size());

        for (int i = 0; i < list2.size(); i++) {
            sb.append(list2.get(i));
        }
        BigInteger int2 = new BigInteger(sb.toString());
        sb.delete(0,list2.size());
        BigInteger int3 = int1.add(int2);

        String str3 = String.valueOf(int3);
        char[] chars = sb.append(str3).reverse().toString().toCharArray();

        ListNode firstNode = new ListNode(Integer.parseInt(chars[0]+""));
        ListNode node = firstNode;
        ListNode nextNode = null;
        for (int i = 1; i < chars.length; i++) {
             nextNode =  new ListNode(Integer.parseInt(chars[i]+""));
             node.next = nextNode;
             node = nextNode;
        }
        return firstNode;

    }

    /**
     * 利用进位机制
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode add02(ListNode l1, ListNode l2) {
        ListNode dummyNode = new ListNode(0);
        ListNode currNode = dummyNode;
        ListNode p =l1;
        ListNode q =l2;
        int carry = 0;
        while( p != null || q != null){
           int x = (p!=null) ? p.val:0;
           int y = (q!=null) ? q.val:0; //短的一方的默认值是0
           int sum = x + y + carry;
           carry = sum / 10;
           currNode.next = new ListNode(sum % 10);
           currNode = currNode.next;
           if(p!=null){
               p = p.next;
           }
            if(q!=null){
                q = q.next;
            }
        }
        if(carry>0){
            currNode.next = new ListNode(carry);
        }

        return dummyNode.next;
    }

   static class ListNode {
        int val;
        ListNode next;
        public  ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;

        ListNode node4 = new ListNode(4);

        ListNode listNode = add02(node1, node4);
        System.out.println(listNode.val);
        while(listNode.next!=null){
            listNode = listNode.next;
            System.out.println(listNode.val);
        }
    }
}
