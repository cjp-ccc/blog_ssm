package com.cjp.dao;

import com.cjp.entity.Blog;
import com.cjp.entity.Link;

import java.util.List;
import java.util.Map;

public interface LinkDao {


    /**
     * 带参数查询博客列表
     * @param map
     * @return
     */
    public List<Link> list(Map<String,Object> map);

    /**
     * 带参数查询博客数量
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 根据主键查询一条博客信息
     */
    public Link findById(Integer id);

    /**
     * 添加一条博客
     */
    public Integer add(Link link);

    /**
     * 修改
     */
    public Integer update(Link link);

    /**
     * 删除
     */
    public Integer delete(Integer id);




}
