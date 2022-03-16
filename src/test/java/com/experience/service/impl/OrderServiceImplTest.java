package com.experience.service.impl;

import com.experience.dataobject.OrderDetail;
import com.experience.dataobject.OrderMaster;
import com.experience.dto.OrderDTO;
import com.experience.repository.OrderDetailRepository;
import com.experience.repository.OrderMasterRepository;
import com.experience.service.OrderService;
import com.experience.utils.JsonUtil;
import com.experience.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private String ORDER_ID = "1646209378193421353";

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小小土");
        orderDTO.setBuyerPhone("123456");
        orderDTO.setBuyerAddress("天空公寓");
        orderDTO.setBuyerOpenid("oTgZpwSXfUsMA3am3bLFradeWnaQ");

        List<OrderDetail> orderDetailList = new LinkedList<>();

//        OrderDetail orderDetail1 = new OrderDetail();
//        orderDetail1.setProductId("1");
//        orderDetail1.setProductQuantity(2);
//        orderDetailList.add(orderDetail1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("2");
        orderDetail2.setProductQuantity(10);
        orderDetailList.add(orderDetail2);

        OrderDetail orderDetail3 = new OrderDetail();
        orderDetail3.setProductId("3");
        orderDetail3.setProductQuantity(20);
        orderDetailList.add(orderDetail3);


        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO new_orderDTO = orderService.create(orderDTO);

        log.info("【创建订单】result={}", new_orderDTO);
        Assert.assertNotEquals(null,new_orderDTO);

    }

    @Test
    public void findOne() throws Exception {

        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询{}号订单】 result = {}",ORDER_ID, JsonUtil.toJson(result));
        Assert.assertNotEquals(0,result.getOrderDetailList().size());
    }

    @Test
    public void findList() throws Exception {
        Page<OrderDTO> orderDTOPage = orderService.findList("0", PageRequest.of(0, 2));
        Assert.assertEquals(1,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne("0");
        orderService.cancel(orderDTO);
        Assert.assertEquals(new Integer(2),orderDTO.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne("0");
        orderService.finish(orderDTO);
        Assert.assertEquals(new Integer(1),orderDTO.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne("0");
        orderService.paid(orderDTO);
        Assert.assertEquals(new Integer(1),orderDTO.getPayStatus());
    }

    @Test
    public void findListTest() throws Exception{
        Page<OrderDTO> orderDTOPage = orderService.findList(PageRequest.of(0, 5));

        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

        //(提示，判断条件)
        Assert.assertTrue("查询所有订单列表",orderDTOPage.getTotalElements() > 0);
    }

}