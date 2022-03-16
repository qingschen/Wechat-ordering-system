package com.experience.utils;

import com.experience.VO.ResultVO;

import java.io.Serializable;

/**
 * Created by fj on 2022/2/19.
 * 将ResultVO类型格式化输出
 */
public class ResultVOUtil{

    public static ResultVO success(Object object){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);

        return resultVO;
    }


    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }
}
