package com.cjp.controller;

import com.cjp.entity.Blogger;
import com.cjp.service.BloggerService;
import com.cjp.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;


/**
 * 博主登陆相关
 */
@Controller
@RequestMapping({"/blogger"})
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @RequestMapping({"/login"})
    public String login(Blogger blogger, HttpServletRequest request){

        String userName = blogger.getUserName();
        String password = blogger.getPassword();

        String pw = CryptographyUtil.md5(password,"java1234");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,pw);
        try{
            //验证成功
            //传递token给shiro的realm
             subject.login(token);
             return request.getContextPath()+"/admin/main";
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("blogger",blogger);
            request.setAttribute("errorInfo","密码错误");
        }
        return "login";
    }

    /**
     * 关于博主
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("blogger",bloggerService.find());

        mav.addObject("mainPage",
                "foreground/blogger/info.jsp");

        mav.addObject("pageTitle","关于博主_个人博客系统");

        mav.setViewName("index");

        return mav;
    }
}
