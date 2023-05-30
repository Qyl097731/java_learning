package com.nju.mine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description 艺术家实体类
 * @date 2023/5/27 0:04
 * @author: qyl
 */
@Data
@AllArgsConstructor
public class Artists {
    private String userId;
    private String name;
    private Integer age;
}
