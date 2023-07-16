package com.nju.converter;

import com.nju.bean.dto.CoolBoyDto;
import com.nju.bean.dto.CoolGirlDto;
import com.nju.bean.po.CoolBoy;
import com.nju.bean.po.CoolGirl;
import com.nju.bean.vo.CoolBoyVo;
import com.nju.constants.GirlTypeEnum;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description mapstruct转换
 * @date 2023/7/16 23:14
 * @author: qyl
 */
@Component
public interface BoyGirlConverter {
//    BoyGirlConverter INTERFACE = Mappers.getMapper(BoyGirlConverter.class);

    @Mappings({
            @Mapping(target = "girlFriends", ignore = true)
    })
    CoolBoyDto po2Dto(CoolBoy coolBoy);

    @Mappings ({
            @Mapping (target = "girlType", expression = "java(girlType(coolGirl.getGirlType()))")
    })
    CoolGirlDto po2Dto(CoolGirl coolGirl);

    List<CoolBoyDto> po2DtoBoys(List<CoolBoy> coolBoyList);

    List<CoolGirlDto> po2DtoGirls(List<CoolGirl> coolGirlList);

    @Mapping (source = "salary",target = "salary",numberFormat = "￥#.00",defaultValue = "￥0.00")
    CoolBoyVo boyDto2Vo(CoolBoyDto coolBoyDto);

    /**
     * 反转
     */
    @InheritInverseConfiguration(name = "boyDto2Vo")
    CoolBoyDto boyVo2Dto(CoolBoyVo coolBoyVo);

    default String girlType(Integer girlTypeCode){
        return GirlTypeEnum.byCode(girlTypeCode)
                .getLabel ();
    }
}
