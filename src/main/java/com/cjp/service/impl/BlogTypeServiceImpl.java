package com.cjp.service.impl;

import com.cjp.dao.BlogTypeDao;
import com.cjp.entity.BlogType;
import com.cjp.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {
    @Autowired
    private BlogTypeDao dao;

    @Override
    public List<BlogType> countList() {
        return dao.countList();
    }

    @Override
    public BlogType findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<BlogType> list(Map<String, Object> paramMap) {
        return dao.list(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return dao.getTotal(paramMap);
    }

    @Override
    public Integer add(BlogType blogType) {
        return dao.add(blogType);
    }

    @Override
    public Integer delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public Integer update(BlogType blogType) {
        return dao.update(blogType);
    }
}
