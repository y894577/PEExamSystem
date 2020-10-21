package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/login")
    private String adminLogin(@RequestParam(value = "adminID") String adminID,
                              @RequestParam(value = "password") String password) {
        return JSONArray.toJSONString(adminService.adminLogin(adminID, password));
    }

}
