package com.nju.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author qyl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private String id;

    private String orderSn;

    private BigDecimal amount;

    private String address;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
