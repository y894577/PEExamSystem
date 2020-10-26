package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminHandlerInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object admin = request.getSession().getAttribute(ConstantUtil.ADMIN_SESSION_KEY);
        if (admin == null) {
            response.sendRedirect("/adminLogin.html");
        }
        return true;
    }
}
