package com.jqproject.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author 姜庆
 * @create 2020-02-17 21:13
 * @desc Buffer的简单使用
 **/
public class BufferDemo {

    public static void main(String[] args) {
        //默认都是非直接的分配内存方式
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("================1==============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //存入
        buffer.put("abcde".getBytes());
        System.out.println("================2==============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //初始化游标,limit会锁定到5
        buffer.flip();
        //注意buffer.limit()只有在flip之后用
        byte[] bytes = new byte[buffer.limit()];
        System.out.println("===============3===============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("===============4===============");
        buffer.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));
        //将旧数据遗忘掉
        buffer.clear();
        buffer.put("123456789".getBytes());
        System.out.println("===============5===============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //标记
        buffer.mark();
        System.out.println("================6==============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        buffer.put("0987654321".getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //重置到标记位置
        buffer.reset();
        System.out.println("================7==============");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //重置到开始位置
        buffer.rewind();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
    }
}
