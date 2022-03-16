package com.experience.utils;

import com.experience.enums.CodeEnum;

/**
 * Created by fj on 2022/3/5.
 * 通过code获取指定枚举类型中的值
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
