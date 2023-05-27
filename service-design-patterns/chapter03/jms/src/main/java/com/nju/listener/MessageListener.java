package com.nju.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 *
 * @author qyl
 */
@Component
public class MessageListener {
    /**
     * 用这个注解去监听 接收消息 进行消息的消费
     */
    @JmsListener(destination = "${queueName}")
    public void receive(String messageString) {
        System.out.println ("receive:" + messageString);
    }
}
