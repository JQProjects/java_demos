package com.jqproject.jdk8.default_method;


interface A2 {
    default void say() {
        System.out.println("A");
    }
}
class B2 implements A2 {
    public void say() {
        System.out.println("B");
    }
}

public class C_Extends_B_Inplements_A  extends B2 implements A2{
    public static void main(String[] args) {
        C_Extends_B_Inplements_A c = new C_Extends_B_Inplements_A();
        c.say(); //B,父类优先
    }
}
