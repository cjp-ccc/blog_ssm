package com.cjp.service.impl;

import com.cjp.dao.BloggerDao;
import com.cjp.entity.Blogger;
import com.cjp.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {
    @Autowired
    private BloggerDao bloggerDao;

    public Blogger getByUserName(String userName) {

        return bloggerDao.getByUserName(userName);
    }

    @Override
    public Integer update(Blogger blogger) {
        SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger);
        return bloggerDao.update(blogger);
    }

    @Override
    public Blogger find() {
        return bloggerDao.find();
    }
}
