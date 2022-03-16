package com.experience.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpDeviceServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by fj on 2022/3/8.
 * 微信开放平台相关的配置，向容器中注入了WeChatOpenConfig
 */
@Component
public class WeChatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpDefaultConfigImpl wxOpenConfigStorage = new WxMpDefaultConfigImpl();

        wxOpenConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxOpenConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());

        return wxOpenConfigStorage;
    }
}
