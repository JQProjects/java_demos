package com.jqproject.jdk8.default_method;

interface A1 {
    default void say(int a) {
        System.out.println("A");
    }

    default void run() {
        System.out.println("A.run");
    }
}
interface B1 extends A1{
    default void say(int a) {
        System.out.println("B");
    }

    default void play() {
        System.out.println("B.play");
    }
}
interface C1 extends A1,B1{

}

public class C_Extends_B_Extends_A implements C1{

    public static void main(String[] args) {
        C_Extends_B_Extends_A d = new C_Extends_B_Extends_A();
        byte a = 1;
        d.play();
        d.run();
        d.say(123); //B,继承子类的say
    }
}
