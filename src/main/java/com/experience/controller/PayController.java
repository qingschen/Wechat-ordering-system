package com.experience.controller;

import com.experience.dto.OrderDTO;
import com.experience.service.impl.OrderServiceImpl;
import com.experience.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by fj on 2022/3/2.
 * 实现微信支付页面的相关逻辑
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayServiceImpl payService;

    //生成预支付订单
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map) throws UnsupportedEncodingException {
        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        //2. 发起支付
        PayResponse payResponse = payService.create(orderDTO);

        //这里传值是传入到create.ftl模板类中
        map.put("payResponse", payResponse);
        returnUrl = returnUrl + "/#/order/" + orderId ;
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create", map);

    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
