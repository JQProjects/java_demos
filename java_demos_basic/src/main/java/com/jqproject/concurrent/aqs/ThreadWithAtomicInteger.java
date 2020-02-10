package com.jqproject.concurrent.aqs;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 姜庆
 * @create 2020-02-05 15:56
 * @desc AtomicInteger的使用方法
 **/
public class ThreadWithAtomicInteger extends Thread {
    //加volatile是不能保证原子性的，只能保证可见性
    //    public static volatile Long count = 0L;
    //添加原子类后可以保证计数的准确性
    public static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000 ; i++) {
//            count++;
            count.incrementAndGet();
        }
        //中间的线程打印的结果可能是在别的线程的技术上进行的累加，故中间值不一定为1000的倍数
        System.out.println(this.getName()+",count="+count.get());
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadWithAtomicInteger[] threadWithAtomicIntegers = new ThreadWithAtomicInteger[10];

        for (int i = 0; i < 10; i++) {
            threadWithAtomicIntegers[i] = new ThreadWithAtomicInteger();
        }
        for (int i = 0; i < 10; i++) {
            threadWithAtomicIntegers[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threadWithAtomicIntegers[i].join();
        }
        System.out.println("final:"+count);
    }
}
