package com.jqproject.tcp_udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author 姜庆
 * @create 2020-02-18 12:08
 * @desc NIO通信的编程方式
 **/
public class NIOClient {

    public static void start() throws IOException {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        buffer.put((new Date().toString()).getBytes());
//        buffer.flip();
//        socketChannel.write(buffer);
//        buffer.clear();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            System.out.println(str);
            buffer.put((new Date().toString()+"\n"+str).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        NIOClient.start();
    }
}
