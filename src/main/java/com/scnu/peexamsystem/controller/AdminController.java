package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    private String adminLogin(@RequestParam(value = "adminID") String adminID,
                              @RequestParam(value = "password") String password,
                              HttpSession session) {
        Map<String, Object> map = adminService.adminLogin(adminID, password);
        if (Integer.parseInt((String) map.get("code")) == 1)
            session.setAttribute("userSession", adminID);
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/logout")
    private String adminLogout(HttpSession session) {
        Map<String, Object> map = adminService.adminLogout((String) session.getAttribute("userSession"));
        if (Integer.parseInt((String) map.get("code")) == 1)
            session.removeAttribute("userSession");
        return JSONArray.toJSONString(map);
    }
}
