package com.experience.repository;

import com.experience.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void findOneTest() throws Exception{
        OrderMaster orderMaster = repository.findById("1").orElse(null);
        String orderAddress = orderMaster.getBuyerAddress();

        Assert.assertNotEquals(null,orderAddress);
    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setBuyerName("小1");
        orderMaster.setBuyerPhone("123456");
        orderMaster.setBuyerAddress("天空公寓");
        orderMaster.setOrderAmount(new BigDecimal(50));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByBuyerOpenidTest(){
        Page<OrderMaster> page = repository.findByBuyerOpenid("0",PageRequest.of(0,2));

        Assert.assertNotEquals(0,page.getTotalElements());
    }
}