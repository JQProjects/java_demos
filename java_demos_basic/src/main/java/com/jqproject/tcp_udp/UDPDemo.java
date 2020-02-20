package com.jqproject.tcp_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author 姜庆
 * @create 2020-02-13 13:59
 * @desc UDP连接的Demo
 **/
public class UDPDemo {
    /**
     * UDP的服务端
     */
    public  static class  UDPServer{
        public void start() throws IOException {
            System.out.println("UDP 服务器准备启动");
            DatagramSocket datagramSocket = new DatagramSocket(8080);
            byte[] buff = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            datagramSocket.receive(packet);
            System.out.println("来源"+packet.getAddress()+",端口号"+packet.getPort());
            String request = new String(packet.getData(), 0, packet.getLength());
            System.out.println("请求的信息为："+request);
            datagramSocket.close();
        }
    }

    /**
     * TCP的客户端
     */
    public  static class  UDPClient{
        public void request() throws IOException {
            System.out.println("UDP 客户端准备启动");
            DatagramSocket datagramSocket = new DatagramSocket();
            String request = "I MISS U！";
            DatagramPacket packet = new DatagramPacket(request.getBytes(), request.getBytes().length, InetAddress.getByName("127.0.0.1"), 8080);
            datagramSocket.send(packet);
            datagramSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                new UDPServer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("=====服务器已经启动，准备接受请求=====");
        new Thread(() -> {
            try {
                new UDPClient().request();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
