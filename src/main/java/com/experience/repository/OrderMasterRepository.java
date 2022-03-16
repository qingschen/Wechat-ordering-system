package com.experience.repository;

import com.experience.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fj on 2022/2/21.
 * 用于封装OrderMaster表增删查改功能的接口
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /** 按照买家的openid查询订单，用分页显示. */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
