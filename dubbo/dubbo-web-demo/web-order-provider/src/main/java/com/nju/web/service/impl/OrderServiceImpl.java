package com.nju.web.service.impl;

import com.nju.web.domain.Order;
import com.nju.web.service.order.OrderService;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qyl
 */
@DubboService
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder() {
        return new Order(UUID.randomUUID().toString().replace("-",""),
                UUID.randomUUID().toString().replace("-",""),
                BigDecimal.TEN,
                "江苏省南京市",
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
