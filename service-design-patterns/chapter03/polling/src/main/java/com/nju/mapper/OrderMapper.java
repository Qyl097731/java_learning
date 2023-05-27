package com.nju.mapper;

import com.nju.entity.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description MOCK DATABASE
 * @date 2023/5/27 22:07
 * @author: qyl
 */
@Component
public class OrderMapper {
    private static Map<String, Order> database = new HashMap<> ();

    public void insert(String requestId, Order order) {
        database.put (requestId, order);
    }

    public Order selectOrder(String requestId) {
        return database.getOrDefault (requestId, null);
    }
}
