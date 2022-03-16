package com.experience.service.impl;

import com.experience.dataobject.SellerInfo;
import com.experience.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/3/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid("oTgZpwSXfUsMA3am3bLFradeWnaQ");
        Assert.assertNotEquals(null,sellerInfo);
    }

}