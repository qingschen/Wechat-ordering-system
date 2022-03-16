package com.experience.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * 用于接收前端填写的数据
 * Created by fj on 2022/3/6.
 * 保存卖家端修改商品信息时，前端传回的数据
 */
@Data
public class ProductForm {

    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品图标 */
    private String productIcon;

    /** 商品类目编号 */
    private Integer categoryType;
}
