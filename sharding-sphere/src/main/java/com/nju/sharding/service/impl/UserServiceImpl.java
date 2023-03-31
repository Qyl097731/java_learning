package com.nju.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.sharding.domain.User;
import com.nju.sharding.service.UserService;
import com.nju.sharding.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author asus
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-04-01 01:17:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




