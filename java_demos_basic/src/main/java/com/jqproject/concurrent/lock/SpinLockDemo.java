package com.jqproject.concurrent.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 姜庆
 * @create 2020-02-08 21:34
 * @desc 自旋锁的简单使用
 **/
public class SpinLockDemo {

    private AtomicReference<Thread> sign = new AtomicReference<>();

    /**
     * 当一个线程 调用这个不可重入的自旋锁去加锁的时候没问题，当再次调用lock()的时候，因为自旋锁的持有引用已经不为空了，该线程对象会误认为是别人的线程持有了自旋锁
     * 使用了CAS原子操作，lock函数将owner设置为当前线程，并且预测原来的值为空。unlock函数将owner设置为null，并且预测值为当前线程。
     */
    public void lock(){
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null,current)){

        }
    }

    /**
     * 当有第二个线程调用lock操作时由于owner值不为空，导致循环一直被执行，直至第一个线程调用unlock函数将owner设置为null，第二个线程才能进入临界区。
     */
    public void unlock(){
        Thread current = Thread.currentThread();
        sign.compareAndSet(current,null);
    }

}
