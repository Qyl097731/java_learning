package com.nju.sharding;

import com.nju.sharding.domain.Dict;
import com.nju.sharding.mapper.DictMapper;
import com.nju.sharding.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @date 2023/4/2 20:54
 * @author: qyl
 */
public class ProxyTest extends BaseTest{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DictMapper dictMapper;

    @Test
    public void testUserSelectAll(){
        System.out.println (userMapper.selectList (null));
    }

    /**
     * 广播表查询
     */
    @Test
    public void testInsertBroadcast(){
        System.out.println (dictMapper.selectList (null));
    }
}
