package com.experience.service.impl;

import com.experience.dataobject.ProductInfo;
import com.experience.dto.CartDTO;
import com.experience.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fj on 2022/2/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    private String PRODUCT_ID = "1";

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("1");
        System.out.println(productInfo.getProductId());
        Assert.assertEquals("1",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertNotEquals(null,list);
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = PageRequest.of(0, 2);
        Page<ProductInfo> page = productService.findAll(request);
        Assert.assertNotEquals(null,page);
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("3");
        productInfo.setProductName("小笼包");
        productInfo.setCategoryType(2);
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(200);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void increaseStockTest() throws Exception {
        CartDTO cartDTO = new CartDTO("1", 20);
        Integer old_stock = productService.findOne("1").getProductStock();
        System.out.println(old_stock);

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(cartDTO);
        productService.increaseStock(cartDTOList);
        Integer current_stock = productService.findOne("1").getProductStock();
        Assert.assertEquals(new Integer(old_stock+20),current_stock);
        System.out.println(current_stock);
    }

    @Test
    public void decreaseStockTest() throws Exception {
        CartDTO cartDTO = new CartDTO("1", 20);
        Integer old_stock = productService.findOne("1").getProductStock();
        System.out.println(old_stock);

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(cartDTO);
        productService.decreaseStock(cartDTOList);
        Integer current_stock = productService.findOne("1").getProductStock();
        Assert.assertEquals(new Integer(old_stock-cartDTO.getProductQuantity()),current_stock);
        System.out.println(current_stock);
    }

    @Test
    public void onSaleTest() throws Exception{
        ProductInfo productInfo = productService.onSale(PRODUCT_ID);
        Assert.assertTrue("商品上架失败",productInfo.getProductStatusEnum() == ProductStatusEnum.UP);
    }

    @Test
    public void offSaleTest() throws Exception{
        ProductInfo productInfo = productService.offSale(PRODUCT_ID);
        Assert.assertTrue("商品下架失败",productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN);
    }
}