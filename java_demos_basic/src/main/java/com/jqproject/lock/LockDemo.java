package com.jqproject.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Res{
    public String name;
    public String gender;
    /**
     * false:不可读可以写，true：可读不可写
     */
    public volatile boolean flag = false;
}

class Input extends Thread{
    Res res;

    public Input(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        long count = 0;
        try {
            LockDemo.lock.lock();

            while (true){
                if(!res.flag){
                    LockDemo.cond.await();
                }
                if(count==0){
                    res.gender = "男";
                    res.name = "李明";
                }else {
                    res.gender = "女";
                    res.name = "晓红";
                }
                count = (count+1)%2;
                res.flag=false;
                LockDemo.cond.signal();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            LockDemo.lock.unlock();
        }

    }
}
class Out extends Thread{
    Res res;

    public Out(Res res) {
        this.res = res;
    }

    @Override
    public void run() {

        try {
            LockDemo.lock.lock();

            while (true) {
                if (res.flag) {
                    LockDemo.cond.await();
                }
                System.out.println(res.name + "," + res.gender);
                res.flag = true;
                LockDemo.cond.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LockDemo.lock.unlock();
        }

    }
}


/**
 * @author 姜庆
 * @create 2020-02-05 17:34
 * @desc Lock和condition进行线程同步
 **/
public class LockDemo {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition cond = lock.newCondition();

    public static void main(String[] args) {

         Res res = new Res();
         Input input = new Input(res);
         Out out = new Out(res);

        input.start();
        out.start();
    }
}
