package com.nju.web.controller;

import com.nju.web.service.order.impl.OrderServiceStub;
import com.nju.web.domain.Order;
import com.nju.web.service.order.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @DubboReference(parameters = {
            "version", "2.0.0",
            "stub", "com.nju.web.service.order.impl.OrderServiceStub"
    })
    private OrderService orderService;

    @GetMapping(value = "/createOrder")
    public Order createOrder() throws InterruptedException {
        return orderService.createOrder ();
    }

    @GetMapping(value = "/hello")
    public String sayHello() {
        return "hello";
    }

}
