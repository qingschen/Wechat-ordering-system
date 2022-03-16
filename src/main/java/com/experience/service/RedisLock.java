package com.experience.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by 廖师兄
 * 2017-08-07 23:55
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        // setIfAbsent()方法即Redis中的setnx方法
        //锁未被占用，上锁成功
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //锁已经被占用

        //获取当前数据库中的记录
        String currentValue = redisTemplate.opsForValue().get(key);
        //若上一次锁的时间，小于当前时间，则锁已经过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间，同时获得锁
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //多线程并发时，第一个执行getAndSet方法的线程获得锁，其他线程依旧需要等待
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}
