package com.jqproject.concurrent.aqs;

import java.util.concurrent.CyclicBarrier;

/**
 * @author 姜庆
 * @create 2020-02-07 19:47
 * @desc CyclicBarrier的简单使用
 **/
public class CyclicBarrierDemo {

    public static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) {
        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName()+"-子线程准备开始...");
            try {
                //每次在任务的开始都等待其他线程上一个任务完成
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程开始任务1...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程结束任务1...");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程开始任务2...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程结束任务2...");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程开始任务3...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-子线程结束任务3...");
            System.out.println(Thread.currentThread().getName()+"-子线程结束退出...");
        };

        for (int i = 0; i < 5; i++) {
            new Thread(r1).start();
        }
    }
}
