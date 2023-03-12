package com.njxzc.mockdata.service.impl;

import com.njxzc.mockdata.entity.Order;
import com.njxzc.mockdata.mapper.OrderMapper;
import com.njxzc.mockdata.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qyl
 * @since 2023-03-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
