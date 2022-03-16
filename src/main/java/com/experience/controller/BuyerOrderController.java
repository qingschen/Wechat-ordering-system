package com.experience.controller;

import com.experience.VO.ResultVO;
import com.experience.converter.OrderForm2OrderDTOConverter;
import com.experience.dto.OrderDTO;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.form.OrderForm;
import com.experience.service.impl.BuyerServiceImpl;
import com.experience.service.impl.OrderServiceImpl;
import com.experience.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fj on 2022/2/25.
 * 买家商品控制层，用于编写买家商品页面逻辑
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }


    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0")Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        //TODO
        //查询出来的OrderDTO中的orderDetail列表永远为null,因为并没有设置，应该是前端并不需要
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }

}
