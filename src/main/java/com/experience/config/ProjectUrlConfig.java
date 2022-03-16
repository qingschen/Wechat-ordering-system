package com.experience.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置项目中的url信息
 */
@Data
@ConfigurationProperties(prefix = "projecturl")
@Component
public class ProjectUrlConfig {

    /** 微信公众平台授权url */
    private String weChatMpAuthorize;

    /** 微信开放平台授权url */
    private String weChatOpenAuthorize;

    /** 点餐系统url */
    public String sell;

}
