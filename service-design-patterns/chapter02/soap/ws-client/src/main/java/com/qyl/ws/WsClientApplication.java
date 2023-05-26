package com.qyl.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端
 * @author qyl
 */
@SpringBootApplication
public class WsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsClientApplication.class, args);
    }
}
