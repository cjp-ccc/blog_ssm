package com.cjp.controller.admin;

import com.cjp.entity.Blog;
import com.cjp.entity.PageBean;
import com.cjp.lucene.BlogIndex;
import com.cjp.service.BlogService;
import com.cjp.util.DateJsonValueProcessor;
import com.cjp.util.ResponseUtil;

import com.cjp.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import net.sf.json.processors.DefaultValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客信息管理
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
    @Autowired
    private BlogService service;

    private BlogIndex blogIndex = new BlogIndex();


    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required = false) String page,
                       @RequestParam(value="rows",required = false) String rows,Blog blog,
                       HttpServletResponse response) throws Exception{
        PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("title", StringUtil.formatLike(blog.getTitle()));

        //分页查询博客类型列表
        List<Blog> blogTypeList = service.list(map);

        //查询总共有多少条数据
        Long total = service.getTotal(map);

        //将数据写入response
        JSONObject result = new JSONObject();
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(blogTypeList,config);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return null;
    }


    /**
     * 保存一条博客信息
     */
    @RequestMapping("/save")
    public String save(Blog blog,HttpServletResponse response) throws Exception{
        int resultTotal = 0;
        if(blog.getId()==null){
            resultTotal =  service.save(blog);

            blogIndex.addIndex(blog);
        }else {
            resultTotal = service.update(blog);

            blogIndex.updateIndex(blog);
        }

        JSONObject result = new JSONObject();
        if(resultTotal>0){
            result.put("success",Boolean.valueOf(true));
        }else {
            result.put("success",Boolean.valueOf(false));
        }
        ResponseUtil.write(response,result);
        return null;
    }


    @RequestMapping("/findById")
    public String findById(@RequestParam("id")String id,HttpServletResponse response) throws Exception{

        Blog blog = service.findById(Integer.parseInt(id));
        JSONObject jsonObject = JSONObject.fromObject(blog);
        ResponseUtil.write(response,jsonObject);
        return null;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("ids")String ids,HttpServletResponse response)throws Exception{
        //从前端页面得到的IDS转换为字符串数组
        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            service.delete(Integer.valueOf(idsStr[i]));
            blogIndex.deleteIndex(idsStr[i]);
        }
        JSONObject result = new JSONObject();

        result.put("success",Boolean.valueOf(true));


        ResponseUtil.write(response,result);

        return null;
    }
}
