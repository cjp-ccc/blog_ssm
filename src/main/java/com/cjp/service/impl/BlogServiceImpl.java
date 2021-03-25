package com.cjp.service.impl;

import com.cjp.dao.BlogDao;
import com.cjp.dao.CommentDao;
import com.cjp.entity.Blog;
import com.cjp.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao dao;
    @Autowired
    private CommentDao commentDao;
    @Override
    public List<Blog> countList() {
        return dao.countList();
    }

    @Override
    public List<Blog> list(Map<String, Object> map) {
        return dao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return dao.getTotal(map);
    }

    @Override
    public Blog findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Integer add(Blog blog) {
        return dao.add(blog);
    }

    @Override
    public Integer update(Blog blog) {


        return dao.update(blog);
    }

    @Override
    public Integer delete(Integer id)
    {
        commentDao.deleteByBlogId(id);
        return dao.delete(id);
    }

    @Override
    public Integer save(Blog blog) {
        return dao.save(blog);
    }

    @Override
    public Blog getLastBlog(Integer id) {
        return dao.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return dao.getNextBlog(id);
    }

    @Override
    public Integer getBlogByTypeId(Integer parmInteger) {
        return dao.getBlogByTypeId(parmInteger);
    }
}
