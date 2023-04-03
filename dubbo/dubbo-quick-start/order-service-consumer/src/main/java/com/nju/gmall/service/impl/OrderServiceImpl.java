package com.nju.gmall.service.impl;

import com.nju.gmall.domain.UserAddress;
import com.nju.gmall.service.OrderService;
import com.nju.gmall.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.List;

/**
 * @description
 * @date 2023/4/4 0:10
 * @author: qyl
 */
public class OrderServiceImpl implements OrderService {

    @DubboReference
    UserService userService;
    // 查询用户收获地址
    @Override
    public void initOrder(String userId) {
        List<UserAddress> addressList = userService.getUserAddressList (userId);
        System.out.println (addressList);
    }
}
