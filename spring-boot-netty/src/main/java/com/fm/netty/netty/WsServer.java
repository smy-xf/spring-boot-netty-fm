package com.fm.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName WsServer.java
 * @Description Websocket服务端启动工具类
 * @createTime 2019/12/12 21:20
 */
@Component
public class WsServer {

    private static class SingletionWsServer {
        static final WsServer instance = new WsServer();
    }

    public static WsServer getInstance() {
        return SingletionWsServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public WsServer() {
        /** 创建主从线程组*/
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        /** 创建启动类*/
        serverBootstrap = new ServerBootstrap();
        /**设置主从线程组 设置nio的双向通道 设置子处理器 */
        serverBootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WsServerIninializer());
    }

    public void startNetty() {
        this.channelFuture = serverBootstrap.bind(8088);
        System.err.println("netty websocket server 启动完毕...");
    }
}
