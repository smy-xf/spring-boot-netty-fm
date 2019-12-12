package com.fm.netty.hello.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName WsServer.java
 * @author xiaofan
 * @version 1.0.0
 * @Description Websocket服务端启动类
 * @createTime 2019/12/2 21:38
 */
public class WsServer {
    public static void main(String[] args) {
        /** 创建主从线程组*/
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        /** 创建启动类*/
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /**设置主从线程组 设置nio的双向通道 设置子处理器 */
        serverBootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WsServerIninializer());
        try {
            /** 启动类绑定监听端口 启动方式为同步*/
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            /** 监听关闭的channel，设置为同步方式 */
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
