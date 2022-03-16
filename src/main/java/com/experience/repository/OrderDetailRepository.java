package com.experience.repository;

import com.experience.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fj on 2022/2/21.
 * 用于封装OrderDetail表增删查改功能的接口
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
