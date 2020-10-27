package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentHandlerInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object status = request.getSession().getAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS);
        String requestURI = request.getRequestURI();
        if (status != null) {
            if ((boolean) status && requestURI.equals("/index.html"))
                return true;
            else if (!(boolean) status && requestURI.equals("/upload.html"))
                return true;
            else
                return false;
        } else {
            return true;
        }
    }
}

