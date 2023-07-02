package com.nju.domain;

/**
 * @description mock订单
 * @date 2023/7/3 0:32
 * @author: qyl
 */
public class Order {
    private Long id;
    private Integer num;

    public Long getId() {
        return id;
    }

    public Order(Long id, Integer num) {
        this.id = id;
        this.num = num;
    }
}
