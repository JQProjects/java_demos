package com.jqproject.concurrent.thread;

/**
 * @author 姜庆
 * @create 2020-02-04 17:23
 * @desc 通过售票来演示多线程的安全问题
 **/
public class TrainSale implements Runnable {

    /**
     * 对于全局变量的竞争使用导致了多线程的不安全
     */
    public static long totalCount = 100;

    @Override
    public void run() {
        while (totalCount > 0) {

            System.out.println(Thread.currentThread().getName() + ",现在售卖第" + (100 - totalCount + 1) + "张票");
            totalCount--;
        }

    }

    public static void main(String[] args) {

        TrainSale t1 = new TrainSale();
        TrainSale t2 = new TrainSale();

        Thread thread1 = new Thread(t1, "窗口1");
        Thread thread2 = new Thread(t2, "窗口2");

        thread1.start();
        thread2.start();
    }

}
