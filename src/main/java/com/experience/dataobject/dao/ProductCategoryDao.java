package com.experience.dataobject.dao;

import com.experience.dataobject.ProductCategory;
import com.experience.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fj on 2022/3/14.
 * 封装MyBatis数据库的增删查改功能
 */
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper mapper;

    public int insert(ProductCategory productCategory){
        return mapper.insertByObject(productCategory);
    }
}
