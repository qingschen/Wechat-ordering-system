package com.experience.dto;

import lombok.Data;

/**
 * 购物车
 * Created by fj on 2022/2/22.
 */
@Data
public class CartDTO {

    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

