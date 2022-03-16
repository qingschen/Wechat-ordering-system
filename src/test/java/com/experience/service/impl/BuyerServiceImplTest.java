package com.experience.service.impl;

import com.experience.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyerServiceImplTest {

    @Autowired
    private BuyerServiceImpl buyerService;

    private String OPENID = "";
    private String ORDER_ID = "";

    @Test
    public void findOrderOne() throws Exception {
        OrderDTO orderDTO = buyerService.findOrderOne(OPENID, ORDER_ID);
        Assert.assertNotEquals(null,orderDTO);
    }

    @Test
    public void cancelOrder() throws Exception {
        OrderDTO orderDTO = buyerService.cancelOrder(OPENID, ORDER_ID);
        Assert.assertNotEquals(null,orderDTO);
    }

}