package com.experience.handler;

import com.experience.VO.ResultVO;
import com.experience.config.ProjectUrlConfig;
import com.experience.exception.SellException;
import com.experience.exception.SellerAuthorizeException;
import com.experience.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fj on 2022/3/9.
 * 异常处理类
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 若发生登录异常，即用户未登录，就跳转到用户登录界面进行登录操作
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public String handlerAuthorizeException(){

        //TODO 微信扫码登录地址
        //第一次扫码成功了，后来就不行了
        //不行就改为注释上的，是可以登录的

        // &state=http%3A%2F%2Fsellproject.nat300.top%2Fsell%2Fwechat%2FqrUserInfo
        return "redirect:"
                .concat("https://open.weixin.qq.com/connect/qrconnect")
                .concat("?appid=wx6ad144e54af67d87")
                .concat("&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2F")
                .concat("oTg")
                .concat("&response_type=code&scope=snsapi_login")
                .concat("&state=http%3A%2F%2Fsellproject.nat300.top%2Fsell%2Fwechat%2FqrUserInfo");
//        return new ModelAndView("redirect:"
//                .concat(projectUrlConfig.getSell())
//                .concat("/sell/seller/login")
//                .concat("?openid=oTg"));
    }


    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
