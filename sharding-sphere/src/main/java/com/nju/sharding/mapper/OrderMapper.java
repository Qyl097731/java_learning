package com.nju.sharding.mapper;

import com.nju.sharding.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.sharding.domain.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author asus
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2023-04-01 20:00:32
* @Entity com.nju.sharding.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select ({"SELECT o.order_no, sum(price * `count`) AS amount",
            "FROM t_order o INNER JOIN t_order_item t on o.user_id = t.user_id",
            "GROUP BY o.order_no"})
    List<OrderVo> getOrderAmount();
}




