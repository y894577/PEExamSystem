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
                                    @RequestParam(value = "queryStatus", required = false) String status,
                                    @RequestParam(value = "currentPageNo") int currentPageNo,
                                    @RequestParam(value = "pageSize") int pageSize) {

        Map<String, Object> map = studentService.queryStudentList(stuName, instituteNo, classNo, grade, status, currentPageNo, pageSize);
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
        if (map.get("code").equals("1")) {
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
        boolean isRemoveSession = session.getAttribute("userSession") == null;
        Map<String, Object> map = studentService.studentLogout(isRemoveSession, stuNo);

        return JSONArray.toJSONString(map);
    }

    @PostMapping("/submit")
    private String submitApplication(Student student, HttpSession session) {
        //需要验证发送请求方的session和实体类session是否一致
        //防止恶意提交
        //测试时加上session.getAttribute("userSession") == null，后期加入过滤器则删除
        boolean isSession = session.getAttribute("userSession") == null || session.getAttribute("userSession").toString().equals(student.getStuNo());
        return JSONArray.toJSONString(studentService.submitApplication(student, isSession));
    }

    @PostMapping("/verify")
    private String modifyStatus(@RequestParam("stuNo") String stuNo,
                                @RequestParam("status") String status) {

        return JSONArray.toJSONString(studentService.modifyStatus(status, stuNo));
    }


}
