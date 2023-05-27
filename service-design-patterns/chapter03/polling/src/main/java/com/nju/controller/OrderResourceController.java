package com.nju.controller;

import com.nju.entity.Order;
import com.nju.srevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 服务控制器，负责请求的转发以及请求的响应
 * @date 2023/5/27 21:50
 * @author: qyl
 */
@RestController
@RequestMapping("/order")
public class OrderResourceController {

    @Autowired
    private OrderService orderService;

    private static String baseURL = "localhost:8080/order/";


    /**
     * @param order
     * @return 客户端轮询请求的地址
     */
    @PostMapping
    public String placeOrder(Order order) {
        String requestId = orderService.placeOrder(order);
        return baseURL + requestId;
    }

    @GetMapping("/{requestId}")
    public Order getOrder(@PathVariable("requestId") String requestId) {
        return orderService.getOrder (requestId);
    }
}
