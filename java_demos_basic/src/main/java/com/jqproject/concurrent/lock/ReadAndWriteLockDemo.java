package com.jqproject.concurrent.lock;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


class CACHE{

}


/**
 * @author 姜庆
 * @create 2020-02-08 21:12
 * @desc 读写锁的简单使用
 **/
public class ReadAndWriteLockDemo {

    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static HashMap<String,String> CACHE = new HashMap<>();

    public static Lock r = readWriteLock.readLock();
    public static Lock w = readWriteLock.writeLock();

    /**
     * 写的时候不允许读和其他写
     * @param key
     * @param value
     */
    public static void put(String key ,String value){
        try {
            w.lock();
            Thread.sleep(new Random().nextInt(1000));
            CACHE.put(key,value);
            System.out.println(Thread.currentThread().getName()+"存入"+key+"="+value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            w.unlock();
        }
    }
    public static String get(String key){
        String value=null;
        try {
            r.lock();
            Thread.sleep(new Random().nextInt(1000));
            value = CACHE.get(key);
            System.out.println(Thread.currentThread().getName()+"获取"+key+"="+value);
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            r.unlock();
        }
        return value;
    }


    public static void main(String[] args) {
        Runnable w1 = () -> {
            for (int i = 0; i < 20 ; i++) {
                put(String.valueOf(i),String.valueOf(i));
            }
        };
        Runnable r1 = () -> {
            for (int i = 0; i < 10; i++) {
                String value = get(String.valueOf(i));
                System.out.println("获取值为："+value);
            }
        };

        new Thread(w1).start();
        new Thread(r1).start();
        new Thread(r1).start();

    }


}
