package com.cjp.controller.admin;

import com.cjp.entity.Link;
import com.cjp.entity.PageBean;
import com.cjp.service.LinkService;
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
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Autowired
    private LinkService linkService;


    @RequestMapping("/save")
    public String save(Link link, HttpServletResponse response) throws Exception{
        int resultTotal = 0;
        if(link.getId()==null){
            resultTotal = linkService.add(link).intValue();
        }else{
            resultTotal = linkService.update(link).intValue();
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

    @RequestMapping("/list")
    public String list(@RequestParam(value = "page",required = false)String page,
                       @RequestParam(value = "rows",required = false)String rows,
                       HttpServletResponse response)throws Exception{
        PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());

        List<Link> list = linkService.list(map);

        Long total = linkService.getTotal(map);


        JSONObject result = new JSONObject();
        JSONArray jsonArray =JSONArray.fromObject(list);
        result.put("rows",jsonArray);
        result.put("total",total);
        ResponseUtil.write(response,result);

        return null;
    }

    @RequestMapping("delete")
    public String delete(@RequestParam("ids")String ids,HttpServletResponse response)throws Exception{

        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            linkService.delete(Integer.valueOf(idsStr[i]));
        }

        JSONObject result = new JSONObject();
        result.put("success",Boolean.valueOf(true));
        ResponseUtil.write(response,result);

        return null;
    }
}
