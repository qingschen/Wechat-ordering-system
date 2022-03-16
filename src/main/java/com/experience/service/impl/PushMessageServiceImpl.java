package com.experience.service.impl;

import com.experience.dto.OrderDTO;
import com.experience.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fj on 2022/3/10.
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService{

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("RvV-2ZVtl9G5qc1F2UxETZ1xhOWGcJUfZInS4b2EurM");
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败，{}",e);
        }
    }
}
