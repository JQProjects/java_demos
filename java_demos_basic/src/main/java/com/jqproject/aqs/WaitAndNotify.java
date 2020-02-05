package com.jqproject.aqs;


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
        synchronized (Res.class){
            while (true){

                if(!res.flag){
                    try {
                        //注意是同一把锁上进行信息传递
                        Res.class.wait();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                Res.class.notify();
            }
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

        synchronized (Res.class){
            while (true) {
                if(res.flag){
                    try {
                        Res.class.wait();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(res.name+","+res.gender);
                res.flag=true;
                Res.class.notify();
            }
        }
    }
}
/**
 * @author 姜庆
 * @create 2020-02-05 16:59
 * @desc 线程通讯和同步
 **/
public class WaitAndNotify {
    public static void main(String[] args) {
        Res res = new Res();
        Input input = new Input(res);
        Out out = new Out(res);
        input.start();
        out.start();
    }
}
