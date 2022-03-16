package com.experience.service.impl;

import com.experience.dataobject.OrderDetail;
import com.experience.dataobject.OrderMaster;
import com.experience.dataobject.ProductInfo;
import com.experience.dto.CartDTO;
import com.experience.dto.OrderDTO;
import com.experience.enums.OrderStatusEnum;
import com.experience.enums.PayStatusEnum;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.repository.OrderDetailRepository;
import com.experience.repository.OrderMasterRepository;
import com.experience.service.OrderService;
import com.experience.service.WebSocket;
import com.experience.utils.KeyUtil;
import com.experience.converter.OrderMaster2OrderDTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fj on 2022/2/21.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单并返回创建的对象
     * @param orderDTO ：前端传回的orderDTO只包含name,phone,address,openid,productId,productQuantity
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO){

        //1. 随机生成一个唯一的订单号
        String orderId = KeyUtil.getUniqueKey();

        List<CartDTO> cartDTOList = new ArrayList<>();//记录商品id和商品数量

        BigDecimal orderAmount = new BigDecimal(0); //订单计算总金额
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){

            //2. 根据订单详情表中的product_id查询数据库中对应的productInfo
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //3. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);


            //4. 订单详情表写入数据库
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo,orderDetail);

            orderDetailRepository.save(orderDetail);

            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }


        //5. 订单写入数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);//该操作会将上面设置的值覆盖掉，因此需要调整顺序,该语句放在最前面
        //默认订单状态和支付状态
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);

        //5. 修改库存
        productService.decreaseStock(cartDTOList);

        //6. 修改返回数据
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);

        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        //数据库中查询目标订单，并输出
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }

        OrderDTO result = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, result);
        result.setOrderDetailList(orderDetailList);

        return result;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        //在数据库中查找openId的订单列表
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.covert(orderMasterPage.getContent());

        //订单详情不需要传给前端
//        for (OrderMaster orderMaster : orderMasterPage) {
//            OrderDTO orderDTO = new OrderDTO();
//            BeanUtils.copyProperties(orderMaster,orderDTO);
//            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
//            result.add(orderDTO);
//        }

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //实例中使用orderDTO中的数据，但查看API中传递进来的数据只有openid和orderId，不知道orderDTO中的数据从哪获取过来的
        //应该是调用了查询吧,这里先显示调用一下findOne()
        //调用cancel()时进行处理
//        orderDTO = findOne(orderDTO.getOrderId());
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}"
                    ,orderMaster.getOrderId(),orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态，修改为取消
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】更新失败，OrderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //修改库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，OrderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        // TODO
        // 这边显示永远是false，但示例不是，不知道会不会影响什么
        if(updateResult == null){
            log.error("【取消订单】更新失败，OrderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不正确，OrderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】支付状态不正确，OrderId={},payStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【支付订单】更新失败，OrderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.covert(orderMasterPage.getContent());

        //订单详情不需要传给前端
//        for (OrderMaster orderMaster : orderMasterPage) {
//            OrderDTO orderDTO = new OrderDTO();
//            BeanUtils.copyProperties(orderMaster,orderDTO);
//            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
//            result.add(orderDTO);

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());


    }
}
