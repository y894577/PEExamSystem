package com.scnu.peexamsystem.config;

import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Magic Gunner
 * @version 1.0
 * @deprecated 1.0基于WebMvcConfigurer，2.0基于WebSecurityConfigurerAdapter
 */
public class StudentHandlerInterception implements HandlerInterceptor {
    /**
     * 学生session拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object student = request.getSession().getAttribute(ConstantUtil.USER_SESSION_KEY);
        Object status = request.getSession().getAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS);
        String requestURI = request.getRequestURI();
        if (student != null && status != null) {
            if (!(boolean) status && requestURI.equals("/studentDetail.html"))
                return false;
            else if ((boolean) status && requestURI.equals("/studentSubmit.html"))
                return false;
        }
        return true;
    }
}

