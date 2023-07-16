package com.nju.service.impl;

import com.nju.service.CopyService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description 复制测试
 * @date 2023/7/13 23:51
 * @author: qyl
 */
@Service
public class CopyServiceImpl implements CopyService {
    @Resource(name = "dozer")
    Mapper mapper;

    @Override
    public void copy(Object src,Object dest) {
        mapper.map(src, dest);
        System.out.println (dest);
    }
}
