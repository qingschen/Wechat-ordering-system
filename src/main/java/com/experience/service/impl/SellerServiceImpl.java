package com.experience.service.impl;

import com.experience.dataobject.SellerInfo;
import com.experience.enums.ResultEnum;
import com.experience.exception.SellException;
import com.experience.repository.SellerInfoRepository;
import com.experience.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fj on 2022/3/8.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {

        return sellerInfoRepository.findByOpenid(openid);
    }
}
