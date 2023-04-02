package com.nju.sharding.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description
 * @date 2023/4/2 16:51
 * @author: qyl
 */
@Data
public class OrderVo {
    private String orderNo;

    private BigDecimal amount;
}
