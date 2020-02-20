package com.jqproject.netty5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author 姜庆
 * @create 2020-02-19 21:36
 * @desc Netty5客户端
 **/
public class Netty5Client {


    public static class ClientHandler extends ChannelHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("接受消息"+(String)msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(pGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
        //以下会出现TCP协议中的粘包现象
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        Thread.sleep(1000);
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("test-123456".getBytes()));
        channelFuture.channel().closeFuture().sync();
        pGroup.shutdownGracefully();

    }
}
