package com.jqproject.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author 姜庆
 * @create 2020-02-07 19:56
 * @desc Semaphore的简单使用
 **/
public class SemaphoreDemo {
    /**
     *只有3个许可，10个线程进行争抢
     */
    public static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {

        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName() + "-子线程准备...");
            int i = semaphore.availablePermits();
            if(i>0){
                System.out.println(Thread.currentThread().getName() + "-空闲资源获取中...");
            }
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "-子线程使用中...");
                Thread.sleep(new Random().nextInt(2000));
                System.out.println(Thread.currentThread().getName() + "-子线程释放了...");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }

        };
        for (int i = 0; i < 10; i++) {
            new Thread(r1).start();
        }

    }

}
