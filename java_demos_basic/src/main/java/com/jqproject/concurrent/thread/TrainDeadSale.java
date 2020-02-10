package com.jqproject.concurrent.thread;

/**
 * @author 姜庆
 * @create 2020-02-04 19:30
 * @desc 死锁的场景
 **/
public class TrainDeadSale implements Runnable {
    /**
     * 对于全局变量的竞争使用导致了多线程的不安全
     */
    public static Long totalCount = 100L;

    public boolean flag = false;

    private byte[] mutex = new byte[0];

    @Override
    public void run() {
        if (flag) {

            while (true) {
                synchronized (mutex) {
                    sale();
                }
            }
        } else {
            while (true) {
                sale();
            }
        }
    }

    /**
     * 同步函数默认使用过的this作为锁对象
     * 静态同步函数默认使用class为所对象，注意两个不是同一个对象
     */
    public synchronized void sale() {
        synchronized (mutex) {
            if (totalCount > 0 ){
                try {
                    Thread.sleep(40);
                } catch (Exception e) {

                }
                System.out.println(Thread.currentThread().getName() + ",现在售卖第" + (100 - totalCount + 1) + "张票");
                totalCount--;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //注意使用同一个线程对象，不然有概率模拟不出来死锁
        //因为两个线程的锁要求是同一个，this和mutex应该和对象一致才对
        TrainDeadSale t1 = new TrainDeadSale();
        Thread thread1 = new Thread(t1,"窗口1");
        thread1.start();
        Thread.sleep(40);
        Thread thread2 = new Thread(t1, "窗口2");
        t1.flag = true;
        thread2.start();
    }
}
