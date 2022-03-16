package com.experience.service.impl;

import com.experience.dto.OrderDTO;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.service.PayService;
import com.experience.utils.JsonUtil;
import com.experience.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fj on 2022/3/2.
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService{

    public static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        //新建PayRequest对象并设置其属性,请求下单接口
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】发起支付，request={}", JsonUtil.toJson(payRequest));

        //调用sdk中的类进行支付，返回PayResponse对象
        PayResponse payResponse = bestPayService.pay(payRequest);

        log.info("【微信支付】发起支付，payResponse={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {

        //获取异步通知结果
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}",JsonUtil.toJson(payResponse));

        //1.验证签名
        //2.验证支付状态
        //1,2在sdk中已经完成验证
        //3.验证支付金额
        //4.验证支付者

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if(orderDTO == null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //BigDecimal使用compareTo进行比较
        if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={}，订单金额={}，支付金额={}"
                    ,payResponse.getOrderId(),orderDTO.getOrderAmount(),payResponse.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }


        //修改订单支付状态
        OrderDTO paidResult = orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
