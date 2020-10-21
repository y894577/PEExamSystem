package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/query/list")
    private String queryStudentList(@RequestParam(value = "queryStuName", required = false) String stuName,
                                    @RequestParam(value = "queryInstituteNo", required = false) String instituteNo,
                                    @RequestParam(value = "queryClassNo", required = false) String classNo,
                                    @RequestParam(value = "queryGrade", required = false) String grade,
                                    @RequestParam(value = "queryStatus", required = false) String status) {

        Map<String, Object> map = studentService.queryStudentList(stuName, instituteNo, classNo, grade, status);
        return JSONArray.toJSONString(map);
    }

    @GetMapping("/query/single")
    private String queryStudent(@RequestParam(value = "stuNo") String stuNo) {

        return JSONArray.toJSONString(studentService.queryStudent(stuNo));
    }

    @PostMapping("/login")
    private String studentLogin(HttpSession session,
                                @RequestParam(value = "stuNo") String stuNo,
                                @RequestParam(value = "password") String password) {

        Map<String, Object> map = studentService.studentLogin(stuNo, password);
        if (Integer.parseInt((String) map.get("code")) == 1) {
            session.setAttribute("userSession", map.get("stuNo"));
        }
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/register")
    private String studentRegister(@RequestParam(value = "stuNo") String stuNo,
                                   @RequestParam(value = "password") String password) {

        return JSONArray.toJSONString(studentService.registerStudent(stuNo, password));
    }

    @PostMapping("/logout")
    private String studentLogout(HttpSession session,
                                 @RequestParam(value = "stuNo") String stuNo) {
        session.removeAttribute("userSession");

        Map<String, Object> map = studentService.studentLogout(stuNo);
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/submit")
    private String submitApplication(Student student) {

        return JSONArray.toJSONString(studentService.submitApplication(student));
    }
}
