package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Magic Gunner
 * @version 1.0
 */
public class AdminHandlerInterception implements HandlerInterceptor {
    /**
     * 管理员session拦截器
     * @return 如果管理员session为空，跳转到登录页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object admin = request.getSession().getAttribute(ConstantUtil.ADMIN_SESSION_KEY);
        if (admin == null) {
            response.sendRedirect("/adminLogin.html");
        }
        return true;
    }
}
