package com.jqproject.concurrent.aqs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 姜庆
 * @create 2020-02-07 20:44
 * @desc BlockingQueue的简单使用
 **/
public class BlockingQDemo {

    public static BlockingQueue<String> bq = new ArrayBlockingQueue<>(3);
    public static volatile boolean  flag = true;

    public static void main(String[] args) throws InterruptedException {

        Runnable r1 = () -> {

            System.out.println(Thread.currentThread().getName()+"生产者开始了");
            int i = 0 ;
            while (flag){
                try {
                    int num = i++;
                    String name = Thread.currentThread().getName();
                    Thread.sleep(1000);
                    bq.put(name+"-"+num); //阻塞的放入
                    System.out.println(name+"生产者生产了"+num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"生产者结束了");

        };

        Runnable r2 = () -> {

            System.out.println(Thread.currentThread().getName()+"消费者开始了");
            int i = 0 ;
            while (flag){
                try {
                    String str = bq.take(); //take是阻塞的
                    System.out.println(Thread.currentThread().getName()+"消费者消费了"+str);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"消费者结束了");

        };

        new Thread(r1).start();
        new Thread(r1).start();
        new Thread(r1).start();
        new Thread(r2).start();

        //等待10秒
        Thread.sleep(10000);
        flag = false;
        System.out.println("退出");
    }

}
