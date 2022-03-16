package com.experience.exception;

import com.experience.enums.ResultEnum;
import lombok.Data;

/**
 * Created by fj on 2022/2/22.
 * 在整个项目中可能会遇到的异常，code+msg
 */
@Data
public class SellException extends RuntimeException {


    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
