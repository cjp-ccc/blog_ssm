package com.cjp.realm;

import com.cjp.entity.Blogger;
import com.cjp.service.BloggerService;
import com.cjp.util.Const;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private BloggerService bloggerService;
    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登陆验证
     * token:令牌，基于用户名密码的令牌
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从令牌中取出用户名
        String userName = (String)token.getPrincipal();
        //让shiro去验证账号密码
        Blogger blogger = this.bloggerService.getByUserName(userName);
        System.out.println("查询到的是"+blogger.getUserName()+blogger.getPassword());
        if(blogger!=null){
            //将blogger放到session里面
            SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_USER,blogger);
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),"xx");
            return authenticationInfo;
        }
        return null;
    }
}

