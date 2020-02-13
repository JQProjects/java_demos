package com.jqproject.design;

/**
 * @author 姜庆
 * @create 2020-02-13 10:29
 * @desc 懒汉式的单例
 **/
public class Singleton_lazy {
    private Singleton_lazy singleton;

    /**
     * 私有的，不能反射和外部new
     */
    private Singleton_lazy(){
        if (holder.singleton!=null){
            throw new IllegalArgumentException();
        }
    }
    /**
     * 采用双层检测锁的方式
     * 有缺点：所线程竞争环境下有可能会返回初始化不完整的类
     * @return
     */
    public Singleton_lazy instance() {
        if (singleton == null) {
            synchronized (Singleton_lazy.class){
                if (singleton == null){
                    singleton = new Singleton_lazy();
                }
            }
        }
        return singleton;
    }

    /**
     * 内部类(不论是静态内部类还是非静态内部类)是延时加载的，也就是说只会在第一次使用时加载。
     * 不使用就不加载，所以可以很好的实现单例模式。
     * 采用内部静态类holder来实现
     */
    private static class holder{
        private static  Singleton_lazy singleton = new Singleton_lazy();

    }

    public Singleton_lazy instance01() {

        return holder.singleton;
    }

}
