package com.nju.gmall.domain;

import java.io.Serializable;

/**
 * @description
 * @date 2023/4/3 23:44
 * @author: qyl
 */
public class UserAddress implements Serializable {
    private Integer id;
    private String userAddress;
    private String userId;
    private String consignee;
    private String phone;
    private String isDefault;

    public UserAddress(Integer id, String userAddress, String userId, String consignee, String phone, String isDefault) {
        this.id = id;
        this.userAddress = userAddress;
        this.userId = userId;
        this.consignee = consignee;
        this.phone = phone;
        this.isDefault = isDefault;
    }
}
