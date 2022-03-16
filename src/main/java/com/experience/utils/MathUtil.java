package com.experience.utils;

/**
 * Created by fj on 2022/3/4.
 * 对两个double类型进行判等
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    public static Boolean equals(Double d1,Double d2){
        Double abs = Math.abs(d1 - d2);

        if(abs < MONEY_RANGE){
            return true;
        }else {
            return  false;
        }

    }
}
