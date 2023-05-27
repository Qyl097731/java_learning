package com.nju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description jms 请求/确认简单案例 ,图方便消费的提供者和消费者放在一个模块了
 * @date 2023/5/27 19:17
 * @author: qyl
 */
@SpringBootApplication
public class JmsApplication {
    public static void main(String[] args) {
        SpringApplication.run (JmsApplication.class,args);
    }
}
