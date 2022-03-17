package com.experience.repository;

import com.experience.dataobject.SellerInfo;
import com.experience.utils.KeyUtil;
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
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    private String OPENID = "oTg";


    @Test
    public void save() throws Exception{
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("小小土");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid(OPENID);
        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(OPENID);

        Assert.assertNotEquals(null,sellerInfo);
    }

}