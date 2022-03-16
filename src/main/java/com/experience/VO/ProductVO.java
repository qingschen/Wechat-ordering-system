package com.experience.VO;

import com.experience.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 输出API的data体内的数据
 * Created by fj on 2022/2/19.
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -8164628423481209272L;

    /** 类目名称 */
    @JsonProperty("name")
    private  String categoryName;

    /** 类目类型 */
    @JsonProperty("type")
    private Integer categoryType;

    /** 商品信息 */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
