package com.experience.service.impl;

import com.experience.dto.OrderDTO;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/3/2.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1646389616621738785");
        payService.create(orderDTO);
    }

    @Test
    public void refundTest() throws Exception{
        OrderDTO orderDTO = orderService.findOne("1646392596046221517");
        RefundResponse refundResponse = payService.refund(orderDTO);
        Assert.assertNotEquals(null,refundResponse);
    }

}