package com.jqproject.concurrent.thread;

/**
 * @author 姜庆
 * @create 2020-02-04 19:11
 * @desc this锁和class锁
 **/
public class TrainSafeSale2 implements Runnable {

    /**
     * 对于全局变量的竞争使用导致了多线程的不安全
     */
    public static Long totalCount = 100L;

    public boolean flag = false;

    @Override
    public void run() {

        if (flag) {
            sale();
        } else {

            while (totalCount > 0) {
                //锁对象应该是一个不可变对象
                synchronized (this) {
                    if (totalCount > 0) {
                        System.out.println(Thread.currentThread().getName() + ",现在售卖第" + (100 - totalCount + 1) + "张票");
                        totalCount--;
                    }
                }
            }
        }
    }

    /**
     * 同步函数默认使用过的this作为锁对象
     * 静态同步函数默认使用class为所对象，注意两个不是同一个对象
     */
    public synchronized void sale() {
        while (totalCount > 0) {

            System.out.println(Thread.currentThread().getName() + ",现在售卖第" + (100 - totalCount + 1) + "张票");
            totalCount--;
        }

    }

    public static void main(String[] args) throws Exception {

        Thread thread1 = new Thread(new TrainSafeSale2(), "窗口1");
        //说明默认的同步函数的锁就是this
        TrainSafeSale2 t1 = new TrainSafeSale2();
        t1.flag = true;
        Thread thread2 = new Thread(t1, "窗口2");

        thread1.start();
        thread2.start();
    }
}
