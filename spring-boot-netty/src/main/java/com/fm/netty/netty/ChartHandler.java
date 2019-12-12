package com.fm.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName ChartHandler.java
 * @Description 处理消息的Handler，TextWebSocketFrame是在netty中为websocket专门处理文本的对象，frame作为一个载体
 * @createTime 2019/12/7 14:35
 */
public class ChartHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用于记录和管理所有客户端的channel
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端发送的消息
        String content = textWebSocketFrame.text();
        System.out.println("客户端发送得消息是：" +content);

        //把消息刷新到客户端
        /*for (Channel chanel: clients) {
            chanel.writeAndFlush(new TextWebSocketFrame("服务器在" + LocalDateTime.now()+ "接收到消息，消息为：" + content));
        }*/
        //和上面方法作用一样
        clients.writeAndFlush(new TextWebSocketFrame("服务器在" + LocalDateTime.now()+ "接收到消息，消息为：" + content));
    }

    /**
     * 当客户端访问服务端，获取channel并放到ChannelGroup中管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    /**
     *  当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
     *  clients.remove(ctx.channel());
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("移除客户端的长iD为：" + ctx.channel().id().asLongText());
        System.out.println("移除客户端的短iD为：" + ctx.channel().id().asShortText());
    }
}
