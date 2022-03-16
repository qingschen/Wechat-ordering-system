package com.experience.repository;

import com.experience.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryRepository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
        Assert.assertEquals(new Integer(1), productCategory.getCategoryType());
    }

    @Test
    public void findAllTest(){
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        System.out.println(productCategoryList);
        Assert.assertEquals(2,productCategoryList.size());
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = productCategoryRepository.findById(1).orElse(null);
        productCategory.setCategoryName("螺蛳粉");
        productCategoryRepository.save(productCategory);
        System.out.println(productCategory);
    }
}