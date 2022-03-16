package com.experience.controller;

import com.experience.VO.ProductInfoVO;
import com.experience.VO.ProductVO;
import com.experience.VO.ResultVO;
import com.experience.dataobject.ProductCategory;
import com.experience.dataobject.ProductInfo;
import com.experience.service.CategoryService;
import com.experience.service.impl.CategoryServiceImpl;
import com.experience.service.impl.ProductServiceImpl;
import com.experience.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 买家商品控制层，以json格式显示商品信息
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;


    @RequestMapping("/list")
    // key的值为函数入参，condition条件满足时，才会存入数据库,若数据库已经存在缓存，也要满足条件才能取缓存
    // unless可以排除结果错误的情况
    @Cacheable(cacheNames = "product",key = "#sellerId", unless = "#result.getCode() != 0")
    public ResultVO list(@RequestParam("sellerId") String sellerId){
        //1.查询所有上架商品(从数据库中取）
        List<ProductInfo> productInfoList = productService.findUpAll();

        //精简方法(java8, lambda)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //获取productInfoList对应的商品中的类目信息
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>(); //data体内列表
        for(ProductCategory productCategory : productCategoryList){
            //productVO，data里面的一层，name，type，foods
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            //最里面一层，商品的部分信息
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();

                    //使用该方法可以完成属性值的拷贝
                    BeanUtils.copyProperties(productInfo,productInfoVO);

                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
