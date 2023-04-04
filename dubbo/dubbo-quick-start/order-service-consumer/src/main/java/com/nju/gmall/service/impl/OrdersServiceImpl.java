package com.nju.gmall.service.impl;

import com.nju.gmall.domain.Address;
import com.nju.gmall.service.OrdersService;
import com.nju.gmall.service.UsersService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @description
 * @date 2023/4/4 0:10
 * @author: qyl
 */
@DubboService
public class OrdersServiceImpl implements OrdersService {

    @DubboReference
    UsersService usersService;
    // 查询用户收获地址
    @Override
    public void initOrder(String userId) {
        List<Address> addressList = usersService.getUserAddressList (userId);
        System.out.println (addressList);
    }
}
