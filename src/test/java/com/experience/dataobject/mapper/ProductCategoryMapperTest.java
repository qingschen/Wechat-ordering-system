package com.experience.dataobject.mapper;

import com.experience.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/3/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "热销榜");
        map.put("category_type", 4);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("饮品");
        productCategory.setCategoryType(5);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);

    }

    @Test
    public void findByCategoryType() throws Exception{
        ProductCategory category = mapper.findByCategoryType(1);
        Assert.assertEquals(new Integer(1),category.getCategoryType());
    }

    @Test
    public void findByCategoryName() throws Exception{
        List<ProductCategory> categoryList = mapper.findByCategoryName("热销榜");
        Assert.assertNotEquals(null,categoryList);
    }

    @Test
    public void updateByCategoryType() throws Exception{
        int result = mapper.updateByCategoryType("热销",4);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(4);
        productCategory.setCategoryName("猜您喜欢");
        int result = mapper.updateByObject(productCategory);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void deleteByCategoryType() throws Exception{
        int result = mapper.deleteByCategoryType(4);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void selectByCategoryType() throws Exception{
        ProductCategory category = mapper.selectByCategoryType(4);
        Assert.assertNotEquals(null,category);
    }
}