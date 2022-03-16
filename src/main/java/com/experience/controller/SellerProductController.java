package com.experience.controller;

import com.experience.dataobject.ProductCategory;
import com.experience.dataobject.ProductInfo;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.form.ProductForm;
import com.experience.service.CategoryService;
import com.experience.service.ProductService;
import com.experience.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by fj on 2022/3/6.
 * 卖家商品控制层，用于对商品信息进行显示、修改商品状态、修改商品信息等功能
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        Page<ProductInfo> productInfoPage = productService.findAll(PageRequest.of(page - 1, size));
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            ProductInfo productInfo = productService.onSale(productId);
        } catch (SellException e) {
            log.error("【商品上架】上架失败，发生异常{}", e);
            map.put("url", "/sell/seller/product/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            ProductInfo productInfo = productService.offSale(productId);
        } catch (SellException e) {
            log.error("【商品上架】上架失败，发生异常{}", e);
            map.put("url", "/sell/seller/product/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("msg", ResultEnum.SUCCESS.getMsg());
        return new ModelAndView("/common/success", map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("/product/index", map);
    }

    @PostMapping("/save")
    // 更新product数据库
//    @CachePut(cacheNames = "product",key = "123")
    // 删除product数据库
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("url","/sell/seller/product/list");
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("/common/error", map);
        }

        try{
            ProductInfo productInfo = new ProductInfo();
            if(StringUtils.isEmpty(productForm.getProductId())){
                productForm.setProductId(KeyUtil.getUniqueKey());
            }else{
                productInfo = productService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("url","/sell/seller/product/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("/common/error", map);
        }

        map.put("url", "/sell/seller/product/list");

        return new ModelAndView("/common/success", map);
    }

}
