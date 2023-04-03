package com.nju.gmall.service;

import com.nju.gmall.domain.UserAddress;

import java.util.List;

/**
 * @description
 * @date 2023/4/3 23:50
 * @author: qyl
 */
public interface UserService {
    /**
     * 根据id返回订单信息
     * @param userId
     * @return
     */
    public List<UserAddress> getUserAddressList(String userId);
}
