package com.nju;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description 在应用程序中添加Jersey配置，并启动Spring Boot应用程序
 * @date 2023/5/27 0:19
 * @author: qyl
 */
@SpringBootApplication
public class RestApplication{
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
