package com.jqproject.concurrent.thread;

/**
 * @author 姜庆
 * @create 2020-02-04 17:39
 * @desc 启用synchronize锁的机制来保证安全性
 **/
public class TrainSafeSale implements Runnable {

    /**
     * 对于全局变量的竞争使用导致了多线程的不安全
     */
    public static Long totalCount = 100L;

    @Override
    public void run() {
        //典型的doublecheck方式做的，也不好
        while(totalCount>0){
            //锁对象应该是一个不可变对象
//            synchronized(totalCount){
            synchronized(this.getClass()){
                if(totalCount>0){
                    System.out.println(Thread.currentThread().getName() + ",现在售卖第" + (100 - totalCount + 1) + "张票");
                    totalCount--;
                }
            }
        }
    }


    public static void main(String[] args) {
        //锁对象的在使用中必须是不可变的，hashcode发生了改变
        System.out.println(System.identityHashCode(totalCount));
        System.out.println(System.identityHashCode(totalCount-1));


        Thread thread1 = new Thread(new TrainSafeSale(), "窗口1");
        Thread thread2 = new Thread(new TrainSafeSale(), "窗口2");

        thread1.start();
        thread2.start();
    }
}
