package com.experience.exception;

import com.experience.enums.ResultEnum;
import lombok.Data;

/**
 * Created by fj on 2022/3/9.
 * 空的异常类，若在授权时发生异常，抛出该异常类，方便捕获后进行异常处理
 */
public class SellerAuthorizeException extends RuntimeException {

}
