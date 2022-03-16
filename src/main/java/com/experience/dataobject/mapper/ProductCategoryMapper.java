package com.experience.dataobject.mapper;

import com.experience.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by fj on 2022/3/14.
 */

public interface ProductCategoryMapper {

    /**
     * @Insert 注解内的语句为mysql语句
     * 通过map方式注入
     * values()中的值从map中取
     * 使用#{key,jdbcType=type}获取map中键为key的值
     * @return 若正确插入，返回1，否则返回0
     */
    @Insert("insert into product_category(category_name, category_type) values(#{category_name,jdbcType=VARCHAR}, #{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    /**
     * 通过Object方式注入
     * values()中的值从Object中取
     * 使用#{attribute,jdbcType=type}获取Object对象中属性名为attribute的值
     * @param productCategory
     * @return
     */
    @Insert("insert into product_category(category_name, category_type) values(#{categoryName,jdbcType=VARCHAR}, #{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);


    /**
     * @Select 注解中写select语句，#{attribute}中的attribute与函数形参保持一致
     * @Results 该注解可以理解成一个数组
     * @Result 将数据库中的值映射到ProductCategory中，使得返回结果为ProductCategory对象
     * 若查出的结果不唯一，返回类型要为list
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType")
    })
    List<ProductCategory> findByCategoryName(String categoryName);


    /**
     * 通过某字段进行更新
     * 在传多个参数时，要使用@param注解
     * 若categoryType对应多个值，则都会进行更新，返回更新的记录数
     * @param categoryName
     * @param categoryType
     * @return
     */
    @Update("update product_category set category_name=#{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName,@Param("categoryType") Integer categoryType);


    //通过对象进行更新
    @Update("update product_category set category_name=#{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);


    ProductCategory selectByCategoryType(Integer categoryType);
}

