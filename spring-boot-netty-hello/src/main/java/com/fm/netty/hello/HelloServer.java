package com.fm.netty.hello;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName HelloServer.java
 * @author xiaofan
 * @version 1.0.0
 * @Description HelloServer
 * @createTime 2019/12/1 23:43
 */
public class HelloServer {
    
    public static void main(String[] args) {
        //创建主从线程组
        //主线程组
        EventLoopGroup parentGroup  = new NioEventLoopGroup();
        //从线程组
        EventLoopGroup  childGroup  = new NioEventLoopGroup();
        try {
            //netty服务器的创建，启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置主从线程组
            serverBootstrap.group(parentGroup,childGroup)
                    //设置nio的双向通道
                    .channel(NioServerSocketChannel.class)
                    //设置子处理器，处理childGroup
                    .childHandler(new HelloServerInitializer());
            //启动server，设置8088为启动端口，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            throw  new  RuntimeException(e);
         }finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
