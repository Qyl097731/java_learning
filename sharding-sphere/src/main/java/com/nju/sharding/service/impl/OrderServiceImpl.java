package com.nju.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.sharding.domain.Order;
import com.nju.sharding.service.OrderService;
import com.nju.sharding.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2023-04-01 20:00:32
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




