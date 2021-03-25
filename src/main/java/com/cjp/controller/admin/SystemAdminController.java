package com.cjp.controller.admin;

import com.cjp.entity.Blog;
import com.cjp.entity.BlogType;
import com.cjp.entity.Blogger;
import com.cjp.entity.Link;
import com.cjp.service.BlogService;
import com.cjp.service.BlogTypeService;
import com.cjp.service.BloggerService;
import com.cjp.service.LinkService;
import com.cjp.util.Const;
import com.cjp.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {
    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private LinkService linkService;
    /**
     * 刷新系统缓存
     */
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request,
                                HttpServletResponse response) throws Exception{

        ServletContext application = RequestContextUtils.findWebApplicationContext(request).getServletContext();

        //博客类别
        List<BlogType> list = blogTypeService.countList();
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST,list);

        //博主信息
        Blogger blogger = bloggerService.find();
        blogger.setPassword(null);
        application.setAttribute(Const.BLOGGER,blogger);

        //按年月分类数量
        List<Blog> blogCountList = blogService.countList();
        application.setAttribute(Const.BLOG_COUNT_LIST,blogCountList);

        //友情链接
        List<Link> linkList = linkService.list(null);
        application.setAttribute(Const.LINK_LIST,linkList);


        JSONObject result = new JSONObject();
        result.put("success",Boolean.TRUE);
        ResponseUtil.write(response,result);
        return null;
    }
}
