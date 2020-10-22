package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.classes.ClassService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;

    @GetMapping("/query/grade")
    private String getGradeList() {
        return JSONArray.toJSONString(classService.getGradeList());
    }

    @GetMapping("/query/list")
    String queryClassList(@RequestParam("instituteNo") String instituteNo,
                          @RequestParam("grade") String grade) {

        return JSONArray.toJSONString(classService.queryClassList(instituteNo, grade));
    }
}
