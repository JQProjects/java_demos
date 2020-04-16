package com.jqproject.netty5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import static com.jqproject.netty5.Netty5Client.DILIMITER_STR;

/**
 * @author 姜庆
 * @create 2020-02-19 10:05
 * @desc 使用netty5开发服务端
 **/
public class Netty5Server {

    public static class ServerHandler extends ChannelHandlerAdapter{
        /**
         * 当通道被调用,执行该方法
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String message = (String) msg;
            System.out.println("recieve:"+message);
            String res = "Server OK.."+DILIMITER_STR;
            ctx.writeAndFlush(Unpooled.copiedBuffer(res.getBytes()));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Netty5 read to start");
        /**
         * pGroup 用于连接请求 cGroup用于io读写
         */
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        NioEventLoopGroup cGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(pGroup,cGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,1024)
                // 3.设置缓冲区与发送区大小
                .option(ChannelOption.SO_SNDBUF,32*1024).option(ChannelOption.SO_RCVBUF,32*1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //0.采用回车换行解码器：LineBasedFrameDecoder lineBasedFrameDecoder = new LineBasedFrameDecoder(1024);
                        //1.采用定长解码器,这里的11是等于我client测试的预期的
//                        socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(11));
                        //2.采用分割符的方式
                        ByteBuf dilimiter_buf = Unpooled.copiedBuffer(DILIMITER_STR.getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,dilimiter_buf));
                        //单纯字符串解码器是没法解决粘包的
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                });
        //sync 是同步方法
        ChannelFuture cf = bootstrap.bind(8080).sync();
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }
}
