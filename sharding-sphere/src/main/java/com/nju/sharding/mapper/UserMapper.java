package com.nju.sharding.mapper;

import com.nju.sharding.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author asus
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2023-04-01 01:17:51
* @Entity com.nju.sharding.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




