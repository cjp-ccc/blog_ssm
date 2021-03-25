package com.cjp.controller;

import com.cjp.entity.Blog;
import com.cjp.entity.PageBean;
import com.cjp.service.BlogService;
import com.cjp.util.PageUtil;
import com.cjp.util.StringUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/"})
public class IndexController {

    @Autowired
    private BlogService blogService;
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page" ,required = false)String page,
                              @RequestParam(value = "typeId" ,required = false)String typeId,
                              @RequestParam(value = "releaseDateStr" ,required = false)String releaseDateStr,
                              HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        mav.addObject("title","个人博客系统");
        if(StringUtil.isEmpty(page)){
            page = "1";
        }

        PageBean pageBean = new PageBean(Integer.parseInt(page),10);
        Map<String,Object> map = new HashMap<>();


        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("typeId",typeId);
        map.put("releaseDateStr",releaseDateStr);


        List<Blog> blogList = blogService.list(map);


        for (Blog blog : blogList){
            List<String> imagesList = blog.getImagesList();
            String blogInfo = blog.getContent();
            Document doc = Jsoup.parse(blogInfo);
            Elements jpgs = doc.select("img[src$=.jpg]");

            for (int i = 0; i < jpgs.size(); i++){
                Element jpg = (Element)jpgs.get(i);
                imagesList.add(jpg.toString());
                if (i == 2) {
                    break;
                }
            }

        }

        mav.addObject("blogList", blogList);

        StringBuffer param = new StringBuffer();
        if(!StringUtil.isEmpty(typeId)){
            param.append("typeId="+typeId+"&");

        }

        if(StringUtil.isNotEmpty(releaseDateStr)){
            param.append("releaseDateStr="+releaseDateStr+"&");
        }


        String pageCode = PageUtil.genPagination(request.getContextPath()+"/index.html",
                Integer.parseInt(page),blogService.getTotal(map),
                10,param.toString());

        mav.addObject("mainPage","foreground/blog/list.jsp");
        mav.addObject("pageCode",pageCode);

        mav.addObject("blogList",blogList);
        return mav;
    }
}
