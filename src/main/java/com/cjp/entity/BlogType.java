package com.cjp.entity;

import java.io.Serializable;

/**
 * 博客类型
 */
public class BlogType implements Serializable {
    private static final long serivalVersionUID = 1L;
    private Integer id; //主键
    private String typeName;//类型名称
    private String orderNo;//序号
    private Integer blogCount;//该类型下博客的数量


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }
}
