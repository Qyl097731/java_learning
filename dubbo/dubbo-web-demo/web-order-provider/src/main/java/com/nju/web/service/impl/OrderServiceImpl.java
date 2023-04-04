package com.nju.web.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.nju.web.domain.Order;
import com.nju.web.service.order.OrderService;
import org.apache.dubbo.config.annotation.DubboService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qyl
 */

/**
 * 超时
 */
//@DubboService(version = "1.0.0",parameters = {
//        "createOrder.timeout","3000"
//})

/**
 * 重试
 */
//@DubboService(version = "1.0.0",parameters = {
//        "createOrder.retires","0",
//        "createOrder.timeout","3000"
//})

/**
 * 正常使用
 */
@DubboService(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

    @Override
    public Order createOrder() throws InterruptedException {
        Thread.sleep(3000);
        return new Order(UUID.randomUUID().toString().replace("-", ""),
                UUID.randomUUID().toString().replace("-", ""),
                BigDecimal.TEN,
                "江苏省南京市",
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
