package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.admin.AdminService;
import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    private String adminLogin(@RequestParam(value = "adminID") String adminID,
                              @RequestParam(value = "password") String password,
                              HttpSession session) {
        Map<String, Object> map = adminService.adminLogin(adminID, password);
        if (Integer.parseInt(String.valueOf(map.get("code"))) == 1)
            session.setAttribute(ConstantUtil.ADMIN_SESSION_KEY, adminID);
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/logout")
    private String adminLogout(HttpSession session) {
        Map<String, Object> map = adminService.adminLogout((String) session.getAttribute(ConstantUtil.ADMIN_SESSION_KEY));
        if (Integer.parseInt(String.valueOf(map.get("code"))) == 1)
            session.removeAttribute(ConstantUtil.ADMIN_SESSION_KEY);
        return JSONArray.toJSONString(map);
    }
}
