package com.nju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description 请求/确认/轮询简单案例 ,图方便消费的提供者和消费者放在一个模块了
 * @date 2023/5/27 22:46
 * @author: qyl
 */
@SpringBootApplication
public class PollingApplication {
    public static void main(String[] args) {
        SpringApplication.run (PollingApplication.class,args);
    }
}
