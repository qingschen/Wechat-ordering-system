package com.experience.service;

import com.experience.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目服务
 */
public interface CategoryService {
    //通过类目id查询某个类目项
    ProductCategory findOne(Integer categoryId);

    //查询表中所有类目
    List<ProductCategory> findAll();

    /** 根据类目名查询类目信息 */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    //保存某类目到数据库
    ProductCategory save(ProductCategory productCategory);
}
