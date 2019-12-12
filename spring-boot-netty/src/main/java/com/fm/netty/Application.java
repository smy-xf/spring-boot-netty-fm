package com.fm.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xiaofan
 * @version 1.0.0
 * @ClassName Application.java
 * @Description SpringBoot启动类，整个项目的入口
 * @createTime 2019/12/12 20:35
 */
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@SpringBootApplication(scanBasePackages = {"com.fm.netty"})
// 扫描mybatis mapper包路径
@MapperScan(basePackages = "com.fm.netty.mapper")
public class Application {
    @Bean
    public SpringUtil getSpingUtil() {
        return new SpringUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
