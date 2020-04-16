package com.jqproject.basic;

public class StringsDemo {

    public static void main(String[] args) {
        String a = "abc"; //只在常量池建立的“abc”
        String b = "abc";
        System.out.println(a==b);//true
        System.out.println(a.equals(b));//true

        String c = new String("abc"); //发现常量池有，同时在堆内存创建一个“abc”
        String d = new String("abc");
        System.out.println(c==d);//false
        System.out.println(c.intern().equals(d.intern()));//true
        System.out.println(c.intern()==d.intern());//true
        System.out.println(c.equals(d));//true

        System.out.println("======================================");

        String s = new String("1");//这一行代码已经在常量池和堆区都新建了一个“1”对象
        s.intern(); //这一行代码发现，常量池里已经有数据了
        String s2 = "1"; //返回常量池里的“1”
        System.out.println(s.intern() == s);
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();//在常量池还没有“11”的情况下，这个intern（）会在常量池里新增一个指向堆区里“11”字符串的指针 （JDK1.7以上版本）
        String s4 = "11"; //常量池发现已经有一个指向S3的指针后 直接返回，最终导致S4指向S3
        System.out.println(s3 == s4);

    }
}
