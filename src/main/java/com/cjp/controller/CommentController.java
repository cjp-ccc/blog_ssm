package com.cjp.controller;

import com.cjp.entity.Blog;
import com.cjp.entity.Comment;
import com.cjp.service.BlogService;
import com.cjp.service.CommentService;
import com.cjp.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前端用户提交评论
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    /**
     * 提交评论
     */
    @RequestMapping("/save")
    public String save(Comment comment, @RequestParam("imageCode")String imageCode,
                       HttpServletRequest request, HttpServletResponse response,
                        HttpSession session)throws Exception{

        int resultTotal = 0;
        String sRand = (String)session.getAttribute("sRand");
        JSONObject result = new JSONObject();

        if (!imageCode.equals(sRand)) {
            result.put("success",Boolean.FALSE);
            result.put("errorInfo","验证码填写错误");


        }else{
            String userIp = request.getRemoteUser();
            comment.setUserIp(userIp);
            if(comment.getId()==null){
                resultTotal = commentService.add(comment);

                //给对应的博客评论数加1
                Blog blog = blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.update(blog);
            }
        }

        if(resultTotal>0){
            result.put("success",Boolean.TRUE);
        }else{
            result.put("success",Boolean.FALSE);
        }
        ResponseUtil.write(response,result);
        return null;
    }
}
