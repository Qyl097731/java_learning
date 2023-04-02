package com.nju.sharding.mapper;

import com.nju.sharding.domain.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author asus
* @description 针对表【t_dict】的数据库操作Mapper
* @createDate 2023-04-02 17:33:00
* @Entity com.nju.sharding.domain.Dict
*/
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}




