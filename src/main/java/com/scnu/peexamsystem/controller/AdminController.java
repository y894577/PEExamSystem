package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/login")
    private String adminLogin(@RequestParam(value = "adminID") String adminID,
                              @RequestParam(value = "password") String password) {
        return JSONArray.toJSONString(adminService.adminLogin(adminID, password));
    }

    @PostMapping("/logout")
    private String adminLogout(HttpSession session) {
        session.removeAttribute("userSession");
        return null;
    }
}
