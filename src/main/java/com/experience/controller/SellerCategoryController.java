package com.experience.controller;

import com.experience.dataobject.ProductCategory;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.form.CategoryForm;
import com.experience.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by fj on 2022/3/7.
 * 卖家类目控制层，图形化界面实现卖家端对于商品类目的查看、修改和新增
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("/category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        ProductCategory category = new ProductCategory();
        if(categoryId != null){
            category = categoryService.findOne(categoryId);
            map.put("category",category);
        }
        return new ModelAndView("/category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("/common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if(categoryForm.getCategoryId() != null){
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("msg", ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("/common/success",map);
    }

}
