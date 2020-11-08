package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Magic Gunner
 * @version 1.0
 */
public class LoginHandlerInterception implements HandlerInterceptor {
    /**
     * 登录拦截器
     * @return 如果所有类型用户session为空，跳转到登录页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截404和500状态码
        if (response.getStatus() == 404){
            response.sendRedirect("/error/404.html");
            return false;
        }

        if (response.getStatus() == 500) {
            response.sendRedirect("/error/500.html");
            return false;
        }

        Object user = request.getSession().getAttribute(ConstantUtil.USER_SESSION_KEY);
        Object admin = request.getSession().getAttribute(ConstantUtil.ADMIN_SESSION_KEY);
        if (user == null && admin == null) {
            response.sendRedirect("/studentLogin.html");
            return false;
        } else {
            return true;
        }

    }


}
