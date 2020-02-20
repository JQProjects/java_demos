package com.jqproject.design;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface House{
    void sale();
}

class Owner implements House{

    @Override
    public void sale() {
        System.out.println("业主自己卖房子");
    }
}

/**
 * 静态代理
 */
class LianJia implements House{
    private Owner owner;

    public LianJia(Owner owner) {
        this.owner = owner;
    }

    @Override
    public void sale() {
        System.out.println("链家地产代理了业主卖房");
        owner.sale();
    }
}

/**
 * @author 姜庆
 * @create 2020-02-13 12:18
 * @desc 3种代理模式
 **/
public class ProxyDemo {

    public static void main(String[] args) {
        //0.静态代理
        Owner owner = new Owner();
        LianJia lianJia = new LianJia(owner);
        lianJia.sale();
        //JDK动态代理
        System.out.println("==================================");
        House  house = (House)Proxy.newProxyInstance(Owner.class.getClassLoader(),
                                                     Owner.class.getInterfaces(),
                                                     (Object proxy, Method method, Object[] arg)->{
                                                                 System.out.println("线上动态代理代理业主卖房");
                                                             return method.invoke(owner, arg);});

        house.sale();
        //CGLIB动态代理
        System.out.println("==================================");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Owner.class);
        MethodInterceptor methodInterceptor = (Object o, Method method, Object[] objects, MethodProxy methodProxy)->
                                                {System.out.println("线下动态代理代理业主卖房");
                                                  return methodProxy.invokeSuper(o,objects);};
        enhancer.setCallback(methodInterceptor);
        House h = (House) enhancer.create();
        h.sale();
    }

}
