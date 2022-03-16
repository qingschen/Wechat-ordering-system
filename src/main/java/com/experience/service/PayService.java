package com.experience.service;

import com.experience.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 * Created by fj on 2022/3/2.
 */
public interface PayService {

    /** 创建预支付订单 */
    PayResponse create(OrderDTO orderDTO);

    /** 异步通知 */
    PayResponse notify(String notifyData);

    /** 退款 */
    RefundResponse refund(OrderDTO orderDTO);
}
