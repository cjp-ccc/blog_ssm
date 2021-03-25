package com.cjp.service.impl;

import com.cjp.dao.LinkDao;
import com.cjp.entity.Link;
import com.cjp.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkDao linkDao ;

    @Override
    public List<Link> list(Map<String, Object> map) {
        return linkDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return linkDao.getTotal(map);
    }

    @Override
    public Link findById(Integer id) {
        return linkDao.findById(id);
    }

    @Override
    public Integer add(Link link) {
        return linkDao.add(link);
    }

    @Override
    public Integer update(Link link) {
        return linkDao.update(link);
    }

    @Override
    public Integer delete(Integer id) {
        return linkDao.delete(id);
    }


}
