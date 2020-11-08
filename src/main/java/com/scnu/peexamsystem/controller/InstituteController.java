package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.institute.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Magic Gunner
 * @version 1.0
 */
@Controller
@RestController
@RequestMapping(value = "/institute")
public class InstituteController {
    @Autowired
    InstituteService instituteService;

    /**
     * 获取institute列表
     * @return 获取结果
     */
    @GetMapping("/query")
    private String queryInstituteList() {

        return JSONArray.toJSONString(instituteService.queryInstituteList());
    }
}
