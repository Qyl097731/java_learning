package com.nju.web.service.order.impl;

import com.nju.web.domain.Order;
import com.nju.web.service.order.OrderService;

/**
 * @description 本地存根，就是就是多加一个代理转发
 * @date 2023/4/4 23:48
 * @author: qyl
 */
public class OrderServiceStub implements OrderService {
    private final OrderService orderService;

    /**
     * 默认传入远程服务
     * @param orderService
     */
    public OrderServiceStub(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Order createOrder() throws InterruptedException {
        System.out.println ("代理开启");
        return orderService.createOrder ();
    }
}
