package com.experience.repository;

import com.experience.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fj on 2022/2/18.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /** 通过商品状态查询商品列表 */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
