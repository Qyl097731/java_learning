package com.nju.component;

import com.nju.entity.Message;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.io.Serializable;
import java.util.Date;

/**
 * jms组件
 *
 * @author qyl
 */
@Component
public class JmsComponent {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    /**
     * 发送消息
     */
    public void send(Message message) {
        jmsMessagingTemplate.convertAndSend (this.queue, message.toString ());
    }

    /**
     * 用这个注解去监听 接收消息
     */
    @JmsListener(destination = "${queueName}")
    public void receive(String messageString) {
        System.out.println ("receive:" + messageString);
    }
}
