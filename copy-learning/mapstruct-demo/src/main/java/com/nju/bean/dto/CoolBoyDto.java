package com.nju.bean.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2023/7/16 23:05
 * @author: qyl
 */
@Data
public class CoolBoyDto {
    private String name;
    private Integer age;
    private BigDecimal salary;
    private List<CoolGirlDto> girlFriends;
}
