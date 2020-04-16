package com.jqproject.concurrent.lock;

import org.openjdk.jol.info.ClassLayout;

public class LockLayoutDemo {

    private static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        //默认开启了 偏向锁的延迟： BiasedLockingStartupDelay = 4000 UseBiasedLocking=true
        //故让它延迟启动一下
        Thread.sleep(10000);

        String a = new String();

        //0.初始应该是无锁可偏向状态
        System.out.println("============无锁可偏向状态101==========");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());

        Thread.sleep(5000);

        synchronized (a){
            //1.应该是偏向状态并记录了线程ID
            System.out.println("============无锁已偏向状态101==========");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }

        Thread.sleep(5000);
        //2.由于计算hashcode后,
        //注意String对象已经将hashcode方法进行重写了，此时必须用System
        // a.hashCode();
        System.identityHashCode(a);
        synchronized (a){
            //2.由于计算hashcode后，存入对象头后，可偏向状态被存入hashcode覆盖，升级为轻量级锁
            System.out.println("============轻量级状态000==========");
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }

        Thread.sleep(10000);
        flag = true;

        //3.让2个线程竞争这把锁
        new Thread(()->{
            while (flag){
                synchronized (a){
                    System.out.println("============重量级状态010==========");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
                flag = false;
            }
        }).start();

        new Thread(()->{
            while (flag){
                synchronized (a){
                    System.out.println("============重量级状态2 010==========");
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
                flag = false;
            }
        }).start();

    }

}
