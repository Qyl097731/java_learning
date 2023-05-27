package com.nju.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @description MQ配置
 * @date 2023/5/27 22:13
 * @author: qyl
 */
@Configuration
public class ActiveMQConfig {
    @Value("order.consume.queue")
    private String queueName;

    @Bean
    public Queue orderConsumeQueue(){
        return new ActiveMQQueue (queueName);
    }
}
