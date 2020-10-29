package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.service.student.StudentService;
import com.scnu.peexamsystem.util.ConstantUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.xml.bind.Element;
import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Value("${upload.file.path}")
    private String filePath;

    @GetMapping("/getStuNo")
    private String getStuNo(HttpSession session) {
        return session.getAttribute(ConstantUtil.USER_SESSION_KEY).toString();
    }

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
        if (Integer.parseInt(String.valueOf(map.get("code"))) == 1) {
            session.setAttribute(ConstantUtil.USER_SESSION_KEY, stuNo);
        }
        boolean isSubmit = (boolean) map.get("submit");
        session.setAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS, isSubmit);
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/register")
    private String studentRegister(@RequestParam(value = "stuNo") String stuNo,
                                   @RequestParam(value = "password") String password) {

        return JSONArray.toJSONString(studentService.registerStudent(stuNo, password));
    }

    @PostMapping("/logout")
    private String studentLogout(HttpSession session) {
        String stuNo = session.getAttribute(ConstantUtil.USER_SESSION_KEY).toString();
        session.removeAttribute(ConstantUtil.USER_SESSION_KEY);
        session.removeAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS);
        boolean isRemoveSession = session.getAttribute(ConstantUtil.USER_SESSION_KEY) == null
                && session.getAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS) == null;
        Map<String, Object> map = studentService.studentLogout(isRemoveSession, stuNo);

        return JSONArray.toJSONString(map);
    }

    @PostMapping("/submit")
    private String submitApplication(@RequestBody Student student, HttpSession session) {
        //需要验证发送请求方的session和实体类session是否一致
        //防止恶意提交
        //测试时加上session.getAttribute(ConstantUtil.USER_SESSION_KEY) == null，后期加入过滤器则删除
        boolean isSession = session.getAttribute(ConstantUtil.USER_SESSION_KEY).toString().equals(student.getStuNo());
        Map<String, Object> map = studentService.submitApplication(student, isSession);
        if (Integer.parseInt(String.valueOf(map.get("code"))) == 1) {
            session.setAttribute(ConstantUtil.USER_SESSION_KEY, student.getStuNo());
            session.setAttribute(ConstantUtil.STUDENT_SUBMIT_STATUS, true);
        }
        return JSONArray.toJSONString(map);
    }

    @PostMapping("/verify")
    private String modifyStatus(@RequestParam("stuNo") String stuNo,
                                @RequestParam("status") String status) {

        return JSONArray.toJSONString(studentService.modifyStatus(status, stuNo));
    }

    @PostMapping("/upload")
    private String uploadImage(MultipartFile file, HttpSession session) throws Exception {

        String stuNo = session.getAttribute(ConstantUtil.USER_SESSION_KEY).toString();

        Map<String, Object> map = studentService.uploadImg(file, stuNo);

        return JSONArray.toJSONString(map);
    }

    @GetMapping("/showimg")
    private String showImg(@RequestParam("stuNo") String stuNo) {
        File file = new File(filePath + stuNo);
        File[] files = file.listFiles();
        if (files != null)
            return "/student/uploadFile/" + stuNo + "/" + files[0].getName();
        else
            return "";
    }
}
