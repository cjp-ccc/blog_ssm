package com.cjp.service;

import com.cjp.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    //添加
    public int add(Comment comment);

    //更新评论
    public int update(Comment comment);

    //评论查询
    public List<Comment> list(Map<String,Object> map);

    //评论数量
    public Long getTotal(Map<String,Object> map);

    //删除评论
    public Integer delete(Integer id);
}
