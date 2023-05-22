package com.qyl.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端
 * @author qyl
 */
@Configuration
@SpringBootApplication
public class WsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }
}
