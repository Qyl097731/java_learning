package com.nju.gmall.service.impl;

import com.nju.gmall.domain.Address;
import com.nju.gmall.service.UsersService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date 2023/4/3 23:51
 * @author: qyl
 */
@DubboService
public class UserServiceImpl implements UsersService {
    @Override
    public List<Address> getUserAddressList(String userId) {
        Address address1 = new Address (1, "北京市", "1", "邱", "12345678910", "010");
        Address address2 = new Address (2, "南京市", "1", "王武", "12345678910", "010");

        return Arrays.asList (address1,address2);
    }
}
