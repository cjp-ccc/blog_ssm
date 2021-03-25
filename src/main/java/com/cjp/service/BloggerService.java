package com.cjp.service;

import com.cjp.entity.Blogger;
import org.springframework.stereotype.Service;


public interface BloggerService {
    public Blogger getByUserName(String paramString);

    public Integer update(Blogger blogger);

    public Blogger find();
}
