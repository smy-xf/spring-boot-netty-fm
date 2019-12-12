package com.fm.netty;

import com.fm.netty.netty.WsServer;
import com.sun.xml.internal.ws.api.WSService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName NettyBooter.java
 * @Description netty服务端启动，在springboot加载完成之后
 * @createTime 2019/12/12 21:31
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                WsServer.getInstance().startNetty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
