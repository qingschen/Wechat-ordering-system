package com.experience.controller;

import com.experience.config.ProjectUrlConfig;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * Created by fj on 2022/2/28.
 * 调用SDK完成微信授权，以及微信开放平台的授权
 */
//返回界面要用@Controller
@RequestMapping("/wechat")
@Controller
@Slf4j
public class WeChatController {

    //通过WechatMpConfig类注入的对象，已经完成了配置
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.构造网页授权url,本项目中为" http://sellproject.nat300.top/sell/wechat/userInfo"
        String url = projectUrlConfig.getWeChatMpAuthorize() + "/sell/wechat/userInfo";
        //生成跳转url，该方法重定向到url的方法执行，执行完毕后返回returnUrl
        //即执行完毕后，redirectUrl = returnUrl
        String redirectUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(url,
                WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，result={}", redirectUrl);

        //return str; //会跳转到url为str的界面，但是str所指界面为静态
        //return "redirect:" + str; //会跳转到url为str的界面，且允许该界面是动态的
        return "redirect:" + redirectUrl;//跳转到/userInfo
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl) {
        //2.获取access_token,两个小时内有效
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try{
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);

        }catch (WxErrorException e){
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }


        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openid={}",openId);

        return "redirect:" + returnUrl + "?openid=" + openId;//跳到returnUrl，并加上了openid
    }

    @GetMapping("/qrAuthorize")
    private String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
        String url = projectUrlConfig.getWeChatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url,
                WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，result={}", redirectUrl);
        return "redirect:" + redirectUrl;//跳转到/userInfo
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                           @RequestParam(value = "state",defaultValue = "http://sellproject.nat300.top/sell/seller/order/list") String returnUrl) {

        //2.获取access_token,两个小时内有效
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try{
            wxOAuth2AccessToken = wxOpenService.getOAuth2Service().getAccessToken(code);

        }catch (WxErrorException e){
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }


        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openid={}",openId);

        return "redirect:" + returnUrl + "?openid=" + openId;//跳到returnUrl，并加上了openid
    }

}
