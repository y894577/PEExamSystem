package com.scnu.peexamsystem.config;

import com.alibaba.fastjson.JSONArray;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录登出处理器
 * @author Magic Gunner
 * @version 2.0
 */
public class LoginHandler {
    static class LoginSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "登录成功");
            map.put("result", authentication);
            map.put("code", 1);
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        }
    }

    static class LoginFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "登录失败");
            map.put("result", e);
            map.put("code", 0);
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        }
    }

    static class LogoutHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "退出登录成功");
            map.put("result", auth);
            map.put("code", 1);
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        }
    }
}

