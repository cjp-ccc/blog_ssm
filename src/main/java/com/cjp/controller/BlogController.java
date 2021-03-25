package com.cjp.controller;

import com.cjp.entity.Blog;
import com.cjp.lucene.BlogIndex;
import com.cjp.service.BlogService;
import com.cjp.service.CommentService;
import com.cjp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private BlogIndex blogIndex = new BlogIndex();

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;


    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id")Integer id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        Blog blog = blogService.findById(id);

        //处理关键字
        String keyWord = blog.getKeyWord();
        if (StringUtil.isEmpty(keyWord)) {
            mav.addObject("keyWords",null);
        }else{
            String[] arr = keyWord.split(" ");
            mav.addObject("keyWords", StringUtil.filterWhite(Arrays.asList(arr)));
        }

        mav.addObject("blog",blog);

        //阅读人数加一
        blog.setClickHit(blog.getClickHit().intValue()+1);
        this.blogService.update(blog);

        //查询评论
        Map<String,Object> map = new HashMap<>();
        map.put("blogId",blog.getId());
        map.put("state",Integer.valueOf(1));
        mav.addObject("commentList",commentService.list(map));



        //上一篇下一篇
        mav.addObject("pageCode",genUpAndDownPageCode(blogService.getLastBlog(id),
                blogService.getNextBlog(id),request.getServletContext().getContextPath()));

        mav.addObject("mainPage","foreground/blog/view.jsp");
        mav.addObject("pageTitle",blog.getTitle()+"_个人博客系统");


        mav.setViewName("index");
        return mav;
    }

    /**
     * 查询上下篇
     */
    public String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
        StringBuffer pageCode = new StringBuffer();
        if ((lastBlog == null) || (lastBlog.getId() == null)) {
            pageCode.append("<p>上一篇：没有了</p>");
        } else {
            pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + ".html'>" + lastBlog.getTitle() + "</a></p>");
        }
        if ((nextBlog == null) || (nextBlog.getId() == null)) {
            pageCode.append("<p>下一篇：没有了</p>");
        } else {
            pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + ".html'>" + nextBlog.getTitle() + "</a></p>");
        }
        return pageCode.toString();
    }


    /**
     * 根据关键字查询
     * @param q
     * @param page
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView q(@RequestParam(value = "q",required = false)String q,
                          @RequestParam(value = "page",required = false)String page,
                          HttpServletRequest request) throws Exception{
        if(StringUtil.isEmpty(page)){
            page = "1";
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage","foreground/blog/result.jsp");
        List<Blog> blogList =  blogIndex.searchBlog(q.trim());

        int toIndex = 0;

        if(blogList.size()>=Integer.parseInt(page)*10){
            toIndex = Integer.parseInt(page)*10;
        }else {
            toIndex = blogList.size();
        }

        mav.addObject("blogList",blogList.subList((Integer.parseInt(page)-1)*10,toIndex));

        mav.addObject("pageCode",genUpAndDownPageCode(Integer.parseInt(page),blogList.size(),q,10,request.getServletContext().getContextPath()));
        mav.addObject("q",q);

        mav.addObject("resultTotal",blogList.size());
        mav.addObject("pageTitle","搜索关键字"+q+"'结果页面_个人博客");

        mav.setViewName("index");

        return mav;
    }

    /**
     * 查询结果翻页功能
     */
    private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
        long totalPage = totalNum.intValue() % pageSize.intValue() == 0 ? totalNum.intValue() / pageSize.intValue() : totalNum.intValue() / pageSize.intValue() + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0L) {
            return "";
        }
        pageCode.append("<nav>");
        pageCode.append("<ul class='pager' >");
        if (page.intValue() > 1) {
            pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page.intValue() - 1) + "&q=" + q + "'>上一页</a></li>");
        } else {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }
        if (page.intValue() < totalPage) {
            pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page.intValue() + 1) + "&q=" + q + "'>下一页</a></li>");
        } else {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }
        pageCode.append("</ul>");
        pageCode.append("</nav>");

        return pageCode.toString();
    }
}
