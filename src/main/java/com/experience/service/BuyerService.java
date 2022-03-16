package com.experience.service;

import com.experience.dto.OrderDTO;

/**
 * Created by fj on 2022/2/26.
 * 买家服务，查询订单和取消订单
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
