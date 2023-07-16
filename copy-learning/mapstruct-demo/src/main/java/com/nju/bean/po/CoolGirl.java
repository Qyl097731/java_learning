package com.nju.bean.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @date 2023/7/16 22:58
 * @author: qyl
 */
@Data
@AllArgsConstructor
public class CoolGirl {
    private String name;
    private Integer age;
    private Integer girlType;
    private BigDecimal beauty;
    private String boyFriends;
}
