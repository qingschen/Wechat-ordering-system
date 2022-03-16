package com.experience.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目表
 * 类名单词首字母大写，数据库表名小写用_分隔，包名直接全小写
 */
@Entity
//@DynamicUpdate注解，可以自动的更新时间
@DynamicUpdate
//@Data注解，在lombok插件的帮助下，可以代替get(),set()方法
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;//类目id,要和表中属性名保持一致

    private String categoryName;//类目名

    private Integer categoryType;//类目编号，unique key

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
