package com.experience.repository;

import com.experience.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 该类对象可以对ProductCategory表进行处理
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //直接定义方法，idea会自动实现功能，注意方法名称必须和类名相对应
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
