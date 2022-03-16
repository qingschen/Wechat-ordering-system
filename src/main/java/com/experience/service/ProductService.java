package com.experience.service;

import com.experience.dataobject.ProductInfo;
import com.experience.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by fj on 2022/2/18.
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /** 查询所有在售的商品 */
    List<ProductInfo> findUpAll();

    /** 查询所有商品，按页显示 */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
