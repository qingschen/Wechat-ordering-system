package com.experience.dataobject;

import com.experience.enums.OrderStatusEnum;
import com.experience.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fj on 2022/2/21.
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认0位未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    /** 每个订单中可能含有多个商品，每个商品对应一个OrderDetail. */
    //这种方法可用但不推荐
    //若不加该注解，数据库表和实体类不匹配，会报错，该注解使得在匹配时忽略该项
//    @Transient
//    private List<OrderDetail> orderDetailList;
}
