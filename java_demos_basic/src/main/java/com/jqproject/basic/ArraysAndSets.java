package com.jqproject.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ArraysAndSets {

    /**
     * 这样删除是错误的，由于每次删除都会影响下标，所以实际上没法清空
     * 种方式的问题在于，删除某个元素后，list的大小发生了变化，而你的索引也在变化，
     * 所以会导致你在遍历的时候漏掉某些元素。比如当你删除第1个元素后，继续根据索引访问第2个元素时，
     * 因为删除的关系后面的元素都往前移动了一位，所以实际访问的是第3个元素。
     * 因此，这种方式可以用在删除特定的一个元素时使用，但不适合循环删除多个元素时使用。
     */
    public static void delete01(){
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
        }
        System.out.println(list);
    }

    /**
     * 会报错的ConcurrentModificationException
     */
    public static void delete02(){
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        for (Integer integer : list) {
            list.remove(3);
            break;//及时break，也可以
        }
        System.out.println(list);
    }

    /**
     * 会OK
     */
    public static void delete03(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if("c".equals(next)){
//                list.remove(next)
                iterator.remove(); //不能使用list的remove 必须使用 iterator的删除
            }
        }
        System.out.println(list);
    }

    public static void main(String[] args) {
//        delete01();
        delete02();
//        delete03();

    }
}
