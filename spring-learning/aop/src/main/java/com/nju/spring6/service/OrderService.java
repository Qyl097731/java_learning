package com.nju.spring6.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author asus
 */
// 目标类
@Component
public class OrderService {
    // 目标方法
    public void generate(){
        System.out.println("订单已生成！");
    }
}
