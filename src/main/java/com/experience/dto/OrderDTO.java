package com.experience.dto;

import com.experience.dataobject.OrderDetail;
import com.experience.enums.OrderStatusEnum;
import com.experience.enums.PayStatusEnum;
import com.experience.utils.EnumUtil;
import com.experience.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * dto data transmission object
 * 该包下的对象用于数据的传输
 * 在OrderMaster类的基础上加上想要的属性
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /** 订单id */
    private String orderId;

    /** 买家姓名. */
    private String buyerName;

    /** 买家电话. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信openid. */

    private String buyerOpenid;

    /** 总金额. */
    private BigDecimal orderAmount;

    /** 订单状态，默认0为新下单. */
    private Integer orderStatus;

    /** 支付状态，默认0位未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 每个订单中可能含有多个商品，每个商品对应一个OrderDetail. */
    private List<OrderDetail> orderDetailList;

    //此注解可以让输出为Json格式时，忽略掉注解的方法
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}
