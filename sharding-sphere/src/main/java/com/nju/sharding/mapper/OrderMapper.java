package com.nju.sharding.mapper;

import com.nju.sharding.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author asus
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2023-04-01 20:00:32
* @Entity com.nju.sharding.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




