package com.jqproject.tcp_udp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 姜庆
 * @create 2020-02-13 20:23
 * @desc TCP连接的测试
 **/
public class TCPDemo {

    public static String ADDRESS = "127.0.0.1";
    public static int PORT = 9999;

    public static class TCPServer {

        private volatile boolean flag = true;
        private String address;
        private int port;
        private ExecutorService pool = Executors.newCachedThreadPool();

        public TCPServer(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public void start() throws IOException {

            ServerSocket serverSocket = new ServerSocket(port, 1024, InetAddress.getByName(address));
            System.out.println(Thread.currentThread().getName() + "服务启动");

            while (flag) {
                Socket socket = serverSocket.accept();
                pool.submit(() -> {
                    try (InputStream inputStream = socket.getInputStream()) {
                        byte[] buf = new byte[1024];
                        int len = inputStream.read(buf);
                        String content = new String(buf, 0, len);
                        System.out.println(Thread.currentThread().getName() + "服务器线程接受信息：" + content);
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            serverSocket.close();
            System.out.println(Thread.currentThread().getName() + "服务退出");
        }

        public void shutdown() {
            flag = false;
            pool.shutdown();
        }

        public static void main(String[] args) throws Exception {
            TCPServer tcpServer = new TCPServer(ADDRESS, PORT);
            tcpServer.start();

            Thread.sleep(10000);
            tcpServer.shutdown();
        }
    }

    public static class TCPClient {
        private String address;
        private int port;
        public TCPClient(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public void send() throws Exception {
            Socket socket = new Socket(InetAddress.getByName(address),port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(("客户端发送信息"+new Date()).getBytes());
            outputStream.flush();
            outputStream.close();
            socket.close();
        }

        public static void main(String[] args) throws Exception {
            TCPClient tcpClient = new TCPClient(ADDRESS, PORT);
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                tcpClient.send();
            }
        }
    }


}
