package com.jqproject.netty5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author 姜庆
 * @create 2020-02-19 21:36
 * @desc Netty5客户端
 **/
public class Netty5Client {

    public static final String DILIMITER_STR = "_e0e";

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
                //1.采用定长解码器,这里的11是等于我server测试返回的预期值
//                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(11));
                //2.采用分割符的方式
                ByteBuf dilimiter_buf = Unpooled.copiedBuffer(DILIMITER_STR.getBytes());
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,dilimiter_buf));
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
        //以下会出现TCP协议中的粘包现象
        String str = "test-123456" + DILIMITER_STR;
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(str.getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(("test-123456"+DILIMITER_STR).getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(("test-123456"+DILIMITER_STR).getBytes()));
        Thread.sleep(1000);
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(("test-123456"+DILIMITER_STR).getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(("test-123456"+DILIMITER_STR).getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer(("test-123456"+DILIMITER_STR).getBytes()));

        channelFuture.channel().closeFuture().sync();
        pGroup.shutdownGracefully();

    }
}
