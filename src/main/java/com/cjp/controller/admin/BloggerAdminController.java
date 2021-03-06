package com.cjp.controller.admin;

import com.cjp.entity.Blogger;
import com.cjp.service.BloggerService;
import com.cjp.util.Const;
import com.cjp.util.CryptographyUtil;
import com.cjp.util.DateUtil;
import com.cjp.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {
    @Autowired
    private BloggerService bloggerService;
    @RequestMapping("/save")
    public String save(@RequestParam("imageFile")MultipartFile imageFile,
                       Blogger blogger, HttpServletRequest request,
                       HttpServletResponse response
    ) throws Exception {
        if(!imageFile.isEmpty()){
            //获取发送请求的地址
            String filePath = request.getServletContext().getRealPath("/")+"static/images/";
            //用当前时间作为文件的名字
            String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
           //创建一下文件
            File file = new File(filePath);
            //判断文件是否存在
            if(!file.exists()){
                //不存在就创建
                file.mkdirs();
            }
            //将文件上传到服务器
            imageFile.transferTo(new File(filePath+imageName));
            blogger.setImageName(imageName);
        }
        StringBuffer result = new StringBuffer();
        int resultTotal = bloggerService.update(blogger).intValue();
        if(resultTotal>0){
            result.append("<script language='javascript'>alert('修改成功');</script>");
        }else{
            result.append("<script language='javascript'>alert('修改失败');</script>");
        }
        ResponseUtil.write(response,result);
        return null;
    }

    @RequestMapping("/find")
    public String find(HttpServletResponse response) throws Exception{
        Blogger blogger = (Blogger)SecurityUtils.getSubject().getSession().getAttribute(Const.CURRENT_USER);
        JSONObject jsonObject = JSONObject.fromObject(blogger);
        ResponseUtil.write(response,jsonObject);
        return null;
    }

    @RequestMapping("/modifyPassword")
    public String modifyPassword(@RequestParam("id")String id,
                                 @RequestParam("newPassword")String newPassword,
                                 HttpServletResponse response)throws Exception{
       Blogger blogger = new Blogger();
       blogger.setId(Integer.parseInt(id));
       blogger.setPassword(CryptographyUtil.md5(newPassword,"java1234")); //加密

       int resultTotal = bloggerService.update(blogger);
       JSONObject result = new JSONObject();
       if(resultTotal>0){
           result.put("success",Boolean.valueOf(true));
       }else {
           result.put("success",Boolean.valueOf(false));
       }
       ResponseUtil.write(response,result);
       return null;
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();

        return "redirect:/login.jsp";
    }
}
