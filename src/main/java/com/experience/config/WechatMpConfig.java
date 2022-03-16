package com.experience.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.WxMpHostConfig;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.config.impl.WxMpMapConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by fj on 2022/3/1.
 * 微信公众账号相关的配置，向容器中注入WxMpService对象wxMpService
 */
@Component
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    //@Bean将注解的方法的返回值作为对象注入到容器中，对象名为方法名
    @Bean
    public WxMpService wxMpService(){
        //创建一个WxMpService对象
        WxMpService wxMpService = new WxMpServiceImpl();
        //设置该对象的配置信息
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        // 设置APPId和APPSecret
        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
