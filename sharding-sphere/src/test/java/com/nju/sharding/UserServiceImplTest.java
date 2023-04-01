package com.nju.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.nju.sharding.domain.Order;
import com.nju.sharding.domain.User;
import com.nju.sharding.mapper.OrderMapper;
import com.nju.sharding.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * @description
 * @date 2023/4/1 13:41
 * @author: qyl
 */
public class UserServiceImplTest extends BaseTest{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 读写分离
     */
    @Test
    public void testInsert(){
        User user = new User ();
        user.setUname ("张三丰");
        userMapper.insert (user);
        Assertions.assertEquals (userMapper.selectCount (null),3);
    }

    /**
     * 事务开启 都在 主库读写
     */
    @Test
    @Transactional
    public void testTransactional(){
        User user = new User ();
        user.setUname ("王五");
        userMapper.insert (user);
        System.out.println (userMapper.selectList (null));
    }

    /**
     * 负载均衡
     */
    @Test
    public void testLoadBalance(){
        System.out.println (userMapper.selectList (null));
        System.out.println (userMapper.selectList (null));
    }

    /**
     * 垂直分库
     */
    @Test
    public void testInsertOrderAndUser(){
        User user = new User ();
        user.setUname ("qyl");
        userMapper.insert (user);

        Order order = new Order ();
        order.setOrderNo ("nju-1");
        order.setUserId (user.getId ());
        order.setAmount (BigDecimal.TEN);
        orderMapper.insert (order);
    }

    @Test
    public void testSelectFromOrderAndUser(){
        System.out.println (userMapper.selectList (null));
        System.out.println (orderMapper.selectList (null));
    }

    /**
     * 水平分片，插入数据测试
     */
    @Test
    public void testInsertOrder(){
        Order order = new Order ();
        order.setOrderNo ("nju-2");
        order.setUserId (1L);
        order.setAmount (BigDecimal.valueOf (100));
        orderMapper.insert (order);
    }

    /**
     * 水平分片，插入数据测试
     */
    @Test
    public void testInsertOrderDatabaseStrategy(){
        for (int i = 0; i < 4; i++) {
            Order order = new Order ();
            order.setOrderNo ("nju" + i);
            order.setUserId (1L + i);
            order.setAmount (BigDecimal.valueOf (100));
            orderMapper.insert (order);
        }
    }

    /**
     * 水平分片：查询所有记录
     */
    @Test
    public void testShardingSelectAll(){
        List<Order> orders = orderMapper.selectList (null);
        System.out.println (orders);
    }

    /**
     * 水平分片：根据user_id查询记录
     */
    @Test
    public void testShardingSelectByUserId(){
        System.out.println (orderMapper.selectList (new QueryWrapper<Order> ().eq ("user_id", 1L)));
    }
}
