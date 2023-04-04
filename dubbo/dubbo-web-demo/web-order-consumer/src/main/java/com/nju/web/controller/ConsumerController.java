package com.nju.web.controller;

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

    @DubboReference
    private OrderService orderService;

    @GetMapping(value = "/createOrder")
    public Order createOrder(){
        return orderService.createOrder();
    }

    @GetMapping(value = "/hello")
    public String sayHello(){
        return "hello";
    }

}
