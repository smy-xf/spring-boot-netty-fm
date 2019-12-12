package com.fm.netty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fm")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello fm!";
    }
}
