package com.jqproject.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;

/**
 * @author 姜庆
 * @create 2020-02-17 21:41
 * @desc 直接缓冲区使用
 **/
public class DirectBufferDemo {

    public static void useDirectBuffer() throws IOException {
        Instant start = Instant.now();

        FileChannel in = FileChannel.open(Paths.get("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取).mp4"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取)-1.mp4"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        MappedByteBuffer inBuffer = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
        MappedByteBuffer outBuffer = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());
        byte[] dfs = new byte[inBuffer.limit()];
        inBuffer.get(dfs);
        outBuffer.put(dfs);
        in.close();
        out.close();
        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println("NIO直接内存1耗时："+duration.toMillis());
    }

    public static void useBIO() throws IOException {
        Instant start = Instant.now();
        try(
             BufferedInputStream in = new BufferedInputStream(new FileInputStream("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取).mp4"));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取)-2.mp4"));

             ) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > -1){
                out.write(buf,0,len);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println("BIO非直接内存耗时："+duration.toMillis());
    }

    public static void useNoneDirectBuffer() throws IOException {
        Instant start = Instant.now();

        try(
                FileInputStream in = new FileInputStream("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取).mp4");
                FileOutputStream out = new FileOutputStream("E:\\BaiduYunDownload\\蚂蚁架构师\\0009-蚂蚁课堂(每特学院)-2期-NIO编程基础\\第二节(Buffer的数据存取)-3.mp4");
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
        ) {
            //使用allocatedirect效率也不高
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while ( inChannel.read(buffer) > -1){
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println("NIO非直接内存耗时："+duration.toMillis());
    }

    public static void main(String[] args) throws IOException {
        //最好
        useDirectBuffer();
        //其次
        useBIO();
        //最差
        useNoneDirectBuffer();
    }

}
