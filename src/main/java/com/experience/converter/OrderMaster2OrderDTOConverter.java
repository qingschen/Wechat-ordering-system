package com.experience.converter;

import com.experience.dataobject.OrderMaster;
import com.experience.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器，将一种类对象转换成另外一种
 * Created by fj on 2022/2/24.
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;

    }

    public static List<OrderDTO> covert(List<OrderMaster> orderMasters){

//        List<OrderDTO> orderDTOList = new ArrayList<>();
//        for(OrderMaster orderMaster : orderMasters){
//            orderDTOList.add(convert(orderMaster));
//        }
//        return orderDTOList;

        // lambda表达式实现
        return orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
