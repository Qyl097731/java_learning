package com.nju.sharding.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @TableName t_order_item
 */
@TableName(value ="t_order_item")
@Data
public class OrderItem implements Serializable {
    /**
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     *
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     *
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     *
     */
    @TableField(value = "count")
    private Integer count;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
