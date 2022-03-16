package com.experience.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by fj on 2022/2/18.
 */
//Getter注解自动加入get()方法
@Getter
public enum  ProductStatusEnum implements CodeEnum{
    UP(0,"在售"),
    DOWN(1,"已下架")
    ;

    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
