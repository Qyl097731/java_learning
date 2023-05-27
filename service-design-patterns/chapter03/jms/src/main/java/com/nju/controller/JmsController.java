package com.nju.controller;

import com.nju.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description activemq 测试 controller
 * @date 2023/5/27 18:17
 * @author: qyl
 */
@RestController
public class JmsController {
    private final Logger logger = LoggerFactory.getLogger (JmsController.class);
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @GetMapping("/send")
    public String send() {
        logger.info ("pushMessage推送消息");
        Message message = new Message ();
        message.setContent ("推送消息");
        message.setData (new Date ());
        jmsTemplate.convertAndSend ("publish.queue",message.toString ());
        return "发送成功";
    }
}
