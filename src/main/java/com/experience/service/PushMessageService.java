package com.experience.service;

import com.experience.dto.OrderDTO;

/**
 * 模板消息推送服务
 * Created by fj on 2022/3/10.
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
