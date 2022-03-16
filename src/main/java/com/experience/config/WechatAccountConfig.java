package com.experience.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

/**
 * 微信账号相关的配置
 * Created by fj on 2022/3/1.
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //公众号appId
    private String mpAppId;

    /** 公众号APPSecret*/
    private String mpAppSecret;

    /** 开发平台appId */
    private String openAppId;

    /** 开放平台APPSecret*/
    private String openAppSecret;

    /** 商户号*/
    private String mchId;

    /** 商户密钥*/
    private String mchKey;

    /** 商户证书路径 */
    private String keyPath;

    /** 微信支付异步通知地址 */
    private String notifyUrl;

}
