package com.nju.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @description activemq 队列配置
 * @date 2023/5/27 18:03
 * @author: qyl
 */
@Configuration
public class ActiveMQConfig {
    @Value("${queueName}")
    private String queueName;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue (queueName);
    }
}

