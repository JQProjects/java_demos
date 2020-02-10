package com.jqproject.concurrent.aqs;

/**
 * @author 姜庆
 * @create 2020-02-05 15:31
 * @desc Volatile的使用原理
 **/
public class ThreadWithVolatile implements Runnable {

    public volatile boolean flag = true;

    @Override
    public void run() {

        System.out.println("子线程开始了....");
        while (flag){

        }
        System.out.println("子线程结束了....");

    }

    public static void main(String[] args) throws InterruptedException {
        ThreadWithVolatile t = new ThreadWithVolatile();
        Thread thread = new Thread(t, "测试可见性");
        thread.start();
        //当不加volatile关键字时，子线程在接受主线程同步信号时发生了延迟，那么就会错失
        //这次的同步机会，导致丢失同步信息，本地的flag不会发生改变，故可能会一直死循环
        //故添加一个sleep作为测试使用
        Thread.sleep(1000);
        //加上volatile就可以保证所有的信息都会实时的进行同步，延迟结束后也会同步，也就是保证所有子线程的可见性
        t.flag = false;
        System.out.println("主线程退出。。。");

    }
}
