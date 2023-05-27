package com.nju.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 消息实体类
 * @date 2023/5/27 18:13
 * @author: qyl
 */
@Data
public class Message implements Serializable {
    private String content;
    private Date data;
}
