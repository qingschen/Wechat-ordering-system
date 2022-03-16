package com.experience.form;

import lombok.Data;

/**
 * Created by fj on 2022/3/7.
 * 保存卖家端修改类目信息时，前端传回的数据
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
}
