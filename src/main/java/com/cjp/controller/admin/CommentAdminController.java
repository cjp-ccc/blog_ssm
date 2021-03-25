package com.cjp.controller.admin;

import com.cjp.entity.Comment;
import com.cjp.entity.PageBean;
import com.cjp.service.CommentService;
import com.cjp.util.DateJsonValueProcessor;
import com.cjp.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/save")
    public String save(Comment comment, HttpServletResponse response) throws Exception{
        int resultTotal = 0;
        if(comment.getId()==null){
            resultTotal = commentService.add(comment);
        }else{
            resultTotal = commentService.update(comment);
        }

        JSONObject result = new JSONObject();

        if(resultTotal>0){
            result.put("success",true);
        }else{
            result.put("success",false);
        }

        ResponseUtil.write(response,result);
        return null;
    }

    @RequestMapping("/list")
    public String list(@RequestParam(value = "page",required = false)String page,
                       @RequestParam(value = "rows",required = false)String rows,
                       @RequestParam(value = "state",required = false)String state,
                       Comment comment,HttpServletResponse response) throws Exception{
        PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<>();

        map.put("start",pageBean.getStart());
        map.put("sizes",pageBean.getPageSize());
        map.put("state",state);

        List<Comment> list = commentService.list(map);

        Long total = commentService.getTotal(map);

        JSONObject result = new JSONObject();
        JsonConfig config = new JsonConfig();

        config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(list,config);

        result.put("rows",jsonArray);
        result.put("total",total);

        ResponseUtil.write(response,result);

        return null;
    }


    @RequestMapping("/delete")
    public String delete(@RequestParam("ids")String ids,HttpServletResponse response) throws Exception{
        String[] idStr = ids.split(",");

        for(int i=0;i<idStr.length;i++){
            commentService.delete(Integer.parseInt(idStr[i]));
        }

        JSONObject result = new JSONObject();

        result.put("success",true);
        ResponseUtil.write(response,result);
        return null;
    }

    /**
     * 审核评论
     */
    @RequestMapping("/review")
    public String review(@RequestParam("ids")String ids,@RequestParam("state")String state,HttpServletResponse response) throws Exception{
        String[] idsStr = ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            Comment comment = new Comment();
            comment.setId(Integer.parseInt(idsStr[i]));
            comment.setState(Integer.parseInt(state));
            commentService.update(comment);
        }

        JSONObject result = new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;
    }
}
