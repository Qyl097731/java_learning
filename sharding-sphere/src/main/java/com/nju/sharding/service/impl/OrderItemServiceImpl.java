package com.nju.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.sharding.domain.OrderItem;
import com.nju.sharding.service.OrderItemService;
import com.nju.sharding.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【t_order_item0】的数据库操作Service实现
* @createDate 2023-04-02 16:33:03
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

}




