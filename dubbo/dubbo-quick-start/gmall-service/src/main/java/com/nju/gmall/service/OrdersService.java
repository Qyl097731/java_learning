package com.nju.gmall.service;

/**
 * @description
 * @date 2023/4/4 0:08
 * @author: qyl
 */
public interface OrdersService {
    /**
     * 查询用户收货地址
     * @param userId
     */
    void initOrder(String userId);
}
