package com.experience.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by fj on 2022/2/19.
 * API中最外层
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 2218731446732240032L;

    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 具体内容,此处的泛型T可以是List类型 */
    private T data;

}
