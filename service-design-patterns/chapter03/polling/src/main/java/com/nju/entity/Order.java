package com.nju.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description 订单实体类
 * @date 2023/5/27 22:00
 * @author: qyl
 */
@Data
public class Order {
    private String requestId;
    private String goodsId;
    private String num;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
