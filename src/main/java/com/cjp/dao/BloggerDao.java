package com.cjp.dao;

import com.cjp.entity.Blogger;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface BloggerDao {
    public Blogger getByUserName(String paramString);

    /**
     * 更新博主对象
     */
    public Integer update(Blogger blogger);

    /**
     * 查询博主
     */
    public Blogger find();

}
