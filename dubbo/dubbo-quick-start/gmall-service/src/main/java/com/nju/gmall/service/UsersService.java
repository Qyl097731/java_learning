package com.nju.gmall.service;


import com.nju.gmall.domain.Address;

import java.util.List;

/**
 * @description
 * @date 2023/4/3 23:50
 * @author: qyl
 */
public interface UsersService {
    /**
     * 根据id返回订单信息
     * @param userId
     * @return
     */
    List<Address> getUserAddressList(String userId);
}
