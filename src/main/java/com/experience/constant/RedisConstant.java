package com.experience.constant;

/**
 * Created by fj on 2022/3/8.
 * 存放与Redis相关的常量TOKEN_PREFIX EXPIRE
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 7200;
}
