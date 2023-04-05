package com.nju.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nju.web.domain.Order;
import com.nju.web.service.order.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qyl
 */
@RestController
public class ConsumerController {

    /**
     * 正常使用
     */
//    @DubboReference
//    private OrderService orderService;

    /**
     * 设置超时
     */
//    @DubboReference(version = "1.0.0",parameters = {
//            "createOrder.timeout","2000"
//    })
//    private OrderService orderService;

    /**
     * 重试机制
     */
//    @DubboReference(version = "1.0.0",parameters = {
//            "createOrder.timeout","2000",
//            "createOrder.retries","1"
//    })

    /**
     * Version 多版本测试
     */
//    @DubboReference(parameters = {
//            "version","*"
//    })

    /**
     * 本地存根
     */
//    @DubboReference(parameters = {
//            "version", "2.0.0",
//            "stub", "com.nju.web.service.order.impl.OrderServiceStub"
//    })
    /**
     * 轮询
     */
    @DubboReference(loadbalance = "roundrobin",parameters = {
            "version", "2.0.0"})
    private OrderService orderService;

    @GetMapping(value = "/createOrder")
    @HystrixCommand(fallbackMethod = "mockOrder")
    public Order createOrder() throws InterruptedException {
        return orderService.createOrder ();
    }

    public Order mockOrder(){
        return new Order(UUID.randomUUID().toString().replace("-", ""),
                UUID.randomUUID().toString().replace("-", ""),
                BigDecimal.TEN,
                "mock",
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @GetMapping(value = "/hello")
    public String sayHello() {
        return "hello";
    }

}
