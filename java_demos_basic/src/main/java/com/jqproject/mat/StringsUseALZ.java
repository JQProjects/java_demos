package com.jqproject.mat;

import java.util.Random;

/**
 *  分析Mat的报告
 */
public class StringsUseALZ {

//    public static final int[] ints = new int[10];
//    public static final Random RANDOM_INT =  new Random(1000);
//
//    static {
//        for (int i = 0; i < 10; i++) {
//            ints[i] = RANDOM_INT.nextInt();
//        }
//    }
//
//    public static void StringCreate(){
//
//        String[] strings = new String[100000];
//
//        for (int i = 0; i < strings.length; i++) {
//            strings[i] = new String(String.valueOf(ints[i % ints.length])).intern();
//        }
//
//    }
//
//    public static void main(String[] args) {
//        StringCreate();
//    }

    static final int MAX = 100000;
    static final String[] arr = new String[MAX];

    public static void main(String[] args) throws Exception {
        //为长度为10的Integer数组随机赋值
        Integer[] sample = new Integer[10];
        Random random = new Random(1000);
        for (int i = 0; i < sample.length; i++) {
            sample[i] = random.nextInt();
        }
        //记录程序开始时间
        long t = System.currentTimeMillis();
        //使用/不使用intern方法为10万个String赋值，值来自于Integer数组的10个数
        for (int i = 0; i < MAX; i++) {
//            arr[i] = new String(String.valueOf(sample[i % sample.length]));
            arr[i] = new String(String.valueOf(sample[i % sample.length])).intern();
        }
        System.out.println((System.currentTimeMillis() - t) + "ms");
        System.gc();
    }

}
