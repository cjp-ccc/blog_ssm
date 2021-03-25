package com.cjp.dao;

import com.cjp.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogDao {
    /**
     * 无参数查询博客列表
     * @return
     */
    public List<Blog> countList();

    /**
     * 带参数查询博客列表
     * @param map
     * @return
     */
    public List<Blog> list(Map<String,Object> map);

    /**
     * 带参数查询博客数量
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 根据主键查询一条博客信息
     */
    public Blog findById(Integer id);

    /**
     * 添加一条博客
     */
    public Integer add(Blog blog);

    /**
     * 修改
     */
    public Integer update(Blog blog);

    /**
     * 删除
     */
    public Integer delete(Integer id);

    /**
     * 保存
     */
    public Integer save(Blog blog);

    /**
     * 根据类型查询博客数量
     */
    public Integer getBlogByTypeId(Integer parmInteger);

    /**
     * 上一篇
     */
    public Blog getLastBlog(Integer id);

    /**
     * 下一篇
     */
    public Blog getNextBlog(Integer id);
}
