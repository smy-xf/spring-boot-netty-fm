package com.fm.netty.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HelloServerInitializer.java
 * @author xiaofan
 * @version 1.0.0
 * @Description netty子处理器
 * @createTime 2019/12/1 21:11
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel获得管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //通过管道添加handler
        //HttpServerCodec是netty提供的助手类，可以理解为拦截器
        //当请求到服务端，需要做解码，相应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        //添加自定义得助手类，返回请求值
        pipeline.addLast("customHandler",new CustomHandler());
    }
}
