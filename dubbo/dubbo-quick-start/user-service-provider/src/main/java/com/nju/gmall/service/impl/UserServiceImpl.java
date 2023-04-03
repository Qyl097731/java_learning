package com.nju.gmall.service.impl;

import com.nju.gmall.domain.UserAddress;
import com.nju.gmall.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @date 2023/4/3 23:51
 * @author: qyl
 */
@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress (1, "北京市", "1", "邱", "12345678910", "010");
        UserAddress address2 = new UserAddress (2, "南京市", "1", "王武", "12345678910", "010");

        return Arrays.asList (address1,address2);
    }
}
