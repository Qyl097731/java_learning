package com.nju.converter;


import com.nju.bean.dto.CoolBoyDto;
import com.nju.bean.dto.CoolGirlDto;
import com.nju.bean.po.CoolBoy;
import com.nju.bean.po.CoolGirl;
import com.nju.bean.vo.CoolBoyVo;
import com.nju.constants.GirlTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2023/7/16 23:37
 * @author: qyl
 */

@ExtendWith (SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class BoyGirlConverterTest {

    @Resource
    BoyGirlConverter boyGirlConverter;

    @Test
    public void test(){
        CoolBoy coolBoy = new CoolBoy ("海王",20, BigDecimal.valueOf (30000.00),"1,2,3");
        CoolBoyDto coolBoyDto = boyGirlConverter.po2Dto (coolBoy);
        System.out.println (coolBoy);

        CoolGirl coolGirl = new CoolGirl ("妹子",22,
                GirlTypeEnum.CUTE.getCode (),BigDecimal.valueOf (100),"");
        CoolGirlDto coolGirlDto = boyGirlConverter.po2Dto (coolGirl);
        System.out.println (coolGirlDto);

        CoolBoy coolBoy2 = new CoolBoy ("张三",26, BigDecimal.valueOf (3000.00),"");
        List<CoolBoyDto> coolBoyList = boyGirlConverter.po2DtoBoys (Arrays.asList(coolBoy,coolBoy2));
        System.out.println (coolBoyList);

        CoolBoyVo boyVo = boyGirlConverter.boyDto2Vo (coolBoyDto);
        System.out.println (boyVo);

    }
}
