package com.experience.service;

import com.experience.dataobject.SellerInfo;

/**
 * Created by fj on 2022/3/8.
 */
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
