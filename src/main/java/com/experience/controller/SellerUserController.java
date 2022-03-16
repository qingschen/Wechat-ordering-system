package com.experience.controller;

import com.experience.config.ProjectUrlConfig;
import com.experience.constant.CookieConstant;
import com.experience.constant.RedisConstant;
import com.experience.dataobject.SellerInfo;
import com.experience.enums.ResultEnum;
import com.experience.service.SellerService;
import com.experience.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by fj on 2022/3/8.
 * 卖家用户控制层，用于控制卖家的登录登出
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){

        //1. openid去和数据库里的数据匹配
        SellerInfo sellerInfoByOpenid = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfoByOpenid == null){
            map.put("msg", ResultEnum.LOGIN_FAIL);
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }

        //2. 设置token至Redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        //Redis一定要设置过期时间
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),
                openid,expire, TimeUnit.SECONDS);

        //3. 设置token值cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
                       Map<String,Object> map){
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //2. 清除Redis
            redisTemplate.opsForValue().getOperations()
                    .delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT);
        map.put("url", projectUrlConfig.getSell() + "/sell/seller/order/list");

        return new ModelAndView("/common/success",map);

    }

}
