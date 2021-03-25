package com.cjp.dao;

import com.cjp.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogTypeDao {
    //无参数查询所有博客类型列表
    public List<BlogType> countList();
    //根据id查询一条博客类型
    public BlogType findById(Integer id);

    //不固定参数查询博客类型
    public List<BlogType> list(Map<String,Object> paramMap);

    //不固定参数查询博客类型数量
    public Long getTotal(Map<String,Object> paramMap);

    //添加博客类型
    public Integer add(BlogType blogType);

    //删除博客类型
    public Integer delete(Integer id);

    //修改博客类型
    public Integer update(BlogType blogType);

}
