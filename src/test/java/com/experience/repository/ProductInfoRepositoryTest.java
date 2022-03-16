package com.experience.repository;

import com.experience.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findOneTest(){
        ProductInfo productInfo = repository.findById("1").orElse(null);
        System.out.println(productInfo);
        Assert.assertNotEquals(null,productInfo);
    }

    @Test
    public void findByProductStatus() throws Exception{
        List<ProductInfo> list = repository.findByProductStatus(0);
        Assert.assertNotEquals(null,list);
        System.out.println(list);
    }
}