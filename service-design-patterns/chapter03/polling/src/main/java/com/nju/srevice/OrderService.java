package com.nju.srevice;

import com.alibaba.fastjson2.JSON;
import com.nju.entity.Order;
import com.nju.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @date 2023/5/27 22:30
 * @author: qyl
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 发到消息队列进行削峰
     * @param order
     * @return
     */
    public String placeOrder(Order order) {
        String requestId = System.currentTimeMillis () + UUID.randomUUID ().toString ();
        order.setRequestId (requestId);
        jmsTemplate.convertAndSend ("order.consume.queue", JSON.toJSONString(order));
        return requestId;
    }

    public Order getOrder(String requestId) {
        return orderMapper.selectOrder (requestId);
    }
}
