package com.experience.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by fj on 2022/3/8.
 */
@Entity
@Data
public class SellerInfo {

    /** 卖家id */
    @Id
    private String sellerId;

    /** 卖家名称 */
    private String username;

    /** 卖家登录密码 */
    private String password;

    /** 卖家openid */
    private String openid;

//    /** 创建时间 */
//    private Date createTime;
//
//    /** 更新时间 */
//    private Date updateTime;

}
