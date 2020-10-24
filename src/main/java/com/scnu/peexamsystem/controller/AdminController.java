package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.admin.AdminService;
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

    @RequestMapping("")
    private ModelAndView admin(){
        ModelAndView mv = new ModelAndView("admin");
        return mv;
    }

    @PostMapping("/login")
    private String adminLogin(@RequestParam(value = "adminID") String adminID,
                              @RequestParam(value = "password") String password,
                              HttpSession session) {
        Map<String, Object> map = adminService.adminLogin(adminID, password);
        if (map.get("code").equals("1"))
            session.setAttribute("userSession", adminID);
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/logout")
    private String adminLogout(HttpSession session) {
        Map<String, Object> map = adminService.adminLogout((String) session.getAttribute("userSession"));
        if (map.get("code") == "1")
            session.removeAttribute("userSession");
        return JSONArray.toJSONString(map);
    }
}
