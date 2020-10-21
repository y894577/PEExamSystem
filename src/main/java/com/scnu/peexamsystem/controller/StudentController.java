package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    //http://localhost:8080/student/query?queryStuName=1&queryInstituteNo=2

    @GetMapping("/query")
    private String queryStudent(@RequestParam(value = "queryStuName", required = false) String stuName,
                                @RequestParam(value = "queryInstituteNo", required = false) String instituteNo,
                                @RequestParam(value = "queryClassNo", required = false) String classNo,
                                @RequestParam(value = "queryGrade", required = false) String grade,
                                @RequestParam(value = "queryStatus", required = false) String status) {
        Map<String, Object> map = studentService.queryStudent(stuName, instituteNo, classNo, grade, status);
        return JSONArray.toJSONString(map);
    }
}
