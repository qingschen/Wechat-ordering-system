package com.experience.utils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Random;

/**
 * Created by fj on 2022/2/22.
 * 生成随机的id
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间 + 随机数
     * synchronized关键字，防止进程冲突
     * @return
     */
    public static synchronized String getUniqueKey() {
        //num是一个随机生成的六位数
        //random.nextInt(a) + b,生成[b,a+b)之间的数
        Integer num = new Random().nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(num);
    }

}
