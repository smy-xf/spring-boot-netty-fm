package com.fm.netty.hello.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName WsServerIninializer.java
 * @Description WsServer子处理器
 * @createTime 2019/12/7 11:31
 */
public class WsServerIninializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        /**
         * 获取管道 ChannelPipeline
         */
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * 用于支持http协议
         * 1、HttpServerCodec：websocket基于http协议，需要HTTP编解码器
         * 2、ChunkedWriteHandler：对写大数据流的支持
         * 3、HttpObjectAggregator：对HttpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
         *                         netty中的编程，几乎都会用到此handler
         */
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 60));

        /**
         * websokct服务器处理的协议，用于指定给客户端连接访问的路由 :/ws
         * WebSocketServerProtocolHandler会帮你处理一些繁重复杂的事
         * 会帮你处理握手动作：handshaking（close、ping、pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        /*
         * 自定义handler
         */
        pipeline.addLast(new ChartHandler());
    }
}
