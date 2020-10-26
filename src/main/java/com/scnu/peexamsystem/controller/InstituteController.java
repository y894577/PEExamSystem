package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.institute.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController
@RequestMapping(value = "/institute")
public class InstituteController {
    @Autowired
    InstituteService instituteService;


    @GetMapping("/query")
    private String queryInstituteList() {

        return JSONArray.toJSONString(instituteService.queryInstituteList());
    }
}
