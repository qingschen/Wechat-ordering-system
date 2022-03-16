package com.experience.repository;

import com.experience.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2");
        orderDetail.setOrderId("1");
        orderDetail.setProductId("1");
        orderDetail.setProductName("麻辣烫");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductIcon("http:");
        orderDetail.setProductQuantity(20);

        OrderDetail result = repository.save(orderDetail);

        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetail = repository.findByOrderId("0");
        Assert.assertNotEquals(0,orderDetail.size());
    }

}