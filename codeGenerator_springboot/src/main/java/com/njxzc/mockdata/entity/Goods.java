package com.njxzc.mockdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author qyl
 * @since 2023-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@TableName("t_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物资ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 重要性
     */
    private Integer importance;

    /**
     * 物资名称
     */
    @TableField("goodsName")
    private String goodsName;

    /**
     * 物资数量
     */
    private Integer num;

    /**
     * 物资状态 Normal 物资够用 Shorted 物资短缺
     */
    private Integer status;
}
