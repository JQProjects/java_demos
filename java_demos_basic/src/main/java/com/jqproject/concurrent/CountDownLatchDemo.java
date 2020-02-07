package com.jqproject.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author 姜庆
 * @create 2020-02-07 19:29
 * @desc CountDownLatch的简单示例
 **/
public class CountDownLatchDemo {

    public static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始了");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "..开始了");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + "..结束了");
            }
        };

        Runnable r2 = () -> {
                    System.out.println(Thread.currentThread().getName() + "..开始了");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                    System.out.println(Thread.currentThread().getName() + "..结束了");
                };

        new Thread(r1).start();

        new Thread(r2).start();


        latch.await();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束了");

    }
}
