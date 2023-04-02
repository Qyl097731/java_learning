package com.nju.sharding.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName t_dict
 */
@TableName(value ="t_dict")
@Data
public class Dict implements Serializable {
    /**
     *
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     *
     */
    @TableField(value = "dict_type")
    private String dictType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
