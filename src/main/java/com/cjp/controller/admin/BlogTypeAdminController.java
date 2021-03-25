package com.cjp.controller.admin;

import com.cjp.entity.BlogType;
import com.cjp.entity.PageBean;
import com.cjp.service.BlogService;
import com.cjp.service.BlogTypeService;
import com.cjp.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {
    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogService blogService;
    /**
     * 博客类型列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required = false) String page,
                       @RequestParam(value="rows",required = false) String rows,
                       HttpServletResponse response) throws Exception{
        PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));


        Map<String,Object> map = new HashMap<String, Object>();

        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());

        //查询博客类型列表
        List<BlogType> blogTypeList = blogTypeService.list(map);

        //查询总共有多少条数据
        Long total = blogTypeService.getTotal(map);

        //将数据写入response
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);
        return null;
    }


    @RequestMapping("/save")
    public String add(BlogType blogType,HttpServletResponse response) throws Exception{
        int resultTotal = 0;
        if(blogType.getId() == null){
            resultTotal = blogTypeService.add(blogType).intValue();
        }else{
            resultTotal = blogTypeService.update(blogType).intValue();
        }

        JSONObject result = new JSONObject();
        if(resultTotal>0){
            result.put("success",Boolean.valueOf(true));

        }else{
            result.put("success",Boolean.valueOf(false));
        }

        ResponseUtil.write(response,result);

        return null;

    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("ids")String ids,HttpServletResponse response)throws Exception{
        //从前端页面得到的IDS转换为字符串数组
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();
        for(int i=0;i<idsStr.length;i++){
            int sum =  blogService.getBlogByTypeId(Integer.valueOf(idsStr[i]));
            if(sum>0) {
                result.put("exist","该博客类型下有博客，不能删除");
            }else{
                blogTypeService.delete(Integer.valueOf(idsStr[i]));
            }
        }


        result.put("success",Boolean.valueOf(true));


        ResponseUtil.write(response,result);

        return null;
    }
}
