package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.classes.ClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Magic Gunner
 * @version 1.0
 */
@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;

    /**
     * 获取年级列表
     * @return 包含年级列表的json格式结果
     */
    @GetMapping("/query/grade")
    private String getGradeList() {
        return JSONArray.toJSONString(classService.getGradeList());
    }

    /**
     * 通过instituteNo和grade查询class表
     * @param instituteNo 学院号
     * @param grade 年级
     * @return 包含查询class结果的json返回结果
     */
    @GetMapping("/query/list")
    String queryClassList(@RequestParam("instituteNo") String instituteNo,
                          @RequestParam("grade") String grade) {

        return JSONArray.toJSONString(classService.queryClassList(instituteNo, grade));
    }
}
