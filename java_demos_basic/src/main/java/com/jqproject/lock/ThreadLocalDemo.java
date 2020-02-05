package com.jqproject.lock;


class ResNumber{
    public ThreadLocal<Integer> local = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public Integer getNumber(){
        Integer i = local.get();
        local.set(i+1);
        return i;
    }
}


/**
 * @author 姜庆
 * @create 2020-02-05 18:03
 * @desc ThreadLocal的简单使用和原理
 **/
public class ThreadLocalDemo  extends Thread{
    private ResNumber resNumber;

    public ThreadLocalDemo(ResNumber resNumber) {
        this.resNumber = resNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(getName()+",number="+resNumber.getNumber());
        }
    }

    public static void main(String[] args) {
        ResNumber resNumber = new ResNumber();
        ThreadLocalDemo localDemo1 = new ThreadLocalDemo(resNumber);
        ThreadLocalDemo localDemo2 = new ThreadLocalDemo(resNumber);
        ThreadLocalDemo localDemo3 = new ThreadLocalDemo(resNumber);
        localDemo1.start();
        localDemo2.start();
        localDemo3.start();
    }
}

