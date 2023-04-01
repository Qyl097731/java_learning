package com.nju.sharding.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @TableName t_order
 */
@TableName(value = "t_order")
@Data
public class Order implements Serializable {
    /**
     * 分布式ID
     */
//    @TableId(value = "id", type = IdType.AUTO)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField(value = "amount")
    private BigDecimal amount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass () != that.getClass ()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId () == null ? other.getId () == null : this.getId ().equals (other.getId ()))
                && (this.getOrderNo () == null ? other.getOrderNo () == null : this.getOrderNo ().equals (other.getOrderNo ()))
                && (this.getUserId () == null ? other.getUserId () == null : this.getUserId ().equals (other.getUserId ()))
                && (this.getAmount () == null ? other.getAmount () == null : this.getAmount ().equals (other.getAmount ()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId () == null) ? 0 : getId ().hashCode ());
        result = prime * result + ((getOrderNo () == null) ? 0 : getOrderNo ().hashCode ());
        result = prime * result + ((getUserId () == null) ? 0 : getUserId ().hashCode ());
        result = prime * result + ((getAmount () == null) ? 0 : getAmount ().hashCode ());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder ();
        sb.append (getClass ().getSimpleName ());
        sb.append (" [");
        sb.append ("Hash = ").append (hashCode ());
        sb.append (", id=").append (id);
        sb.append (", orderNo=").append (orderNo);
        sb.append (", userId=").append (userId);
        sb.append (", amount=").append (amount);
        sb.append (", serialVersionUID=").append (serialVersionUID);
        sb.append ("]");
        return sb.toString ();
    }
}
