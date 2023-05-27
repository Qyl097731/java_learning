package com.nju.listener;

import com.alibaba.fastjson2.JSON;
import com.nju.entity.Order;
import com.nju.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description 消费者对类
 * @date 2023/5/27 22:18
 * @author: qyl
 */
@Component
public class OrderListener {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 假装进行操作之后，存入数据库
     *
     * @param jsonString
     */
    @JmsListener(destination = "${queueName}")
    public void consumeOrder(String jsonString) {
        Order order = JSON.parseObject (jsonString, Order.class);
        orderMapper.insert (order.getRequestId (), order);
    }
}
