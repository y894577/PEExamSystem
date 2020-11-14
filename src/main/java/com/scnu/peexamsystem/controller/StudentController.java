package com.scnu.peexamsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.service.student.StudentService;
import com.scnu.peexamsystem.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @version 1.0
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Value("${upload.file.path}")
    private String filePath;

    /**
     * 通过session获取StuNo
     *
     * @param principal principal
     * @return stuNo
     */
    @GetMapping("/getStuNo")
    private String getStuNo(Principal principal) {
        return principal.getName();
    }

    /**
     * 根据参数获取学生列表
     *
     * @param stuName       学生姓名
     * @param instituteNo   学院号
     * @param classNo       班级号
     * @param grade         年级
     * @param status        审核状态
     * @param currentPageNo 当前页标
     * @param pageSize      每一页展示条目数
     * @return 封装好的包含学生列表的json对象
     */
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

    /**
     * 通过StuNo查询数据库中Student表
     *
     * @param stuNo 学号
     * @return 封装好的json格式查询结果
     */
    @GetMapping("/query/single")
    private String queryStudent(@RequestParam(value = "stuNo") String stuNo) {

        return JSONArray.toJSONString(studentService.queryStudent(stuNo));
    }

    /**
     * 学生登录
     *
     * @param session  session
     * @param stuNo    学号
     * @param password 密码
     * @return 登录成功/失败结果
     */
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

    /**
     * 学生注册
     *
     * @param stuNo    学号
     * @param password 密码
     * @return 注册成功/失败结果
     */
    @PostMapping("/register")
    private String studentRegister(@RequestParam(value = "stuNo") String stuNo,
                                   @RequestParam(value = "password") String password) {

        return JSONArray.toJSONString(studentService.registerStudent(stuNo, password));
    }

    /**
     * 学生退出登录
     *
     * @param session session
     * @return 退出登录成功/失败结果
     */
    @PostMapping("/logout")
    private String studentLogout(Principal principal) {
        String stuNo = principal.getName();
        Map<String, Object> map = studentService.studentLogout(stuNo);

        return JSONArray.toJSONString(map);
    }

    /**
     * 学生提交完善个人信息表格
     *
     * @param student 学生实体类（json自动转换）
     * @return 提交成功/失败结果
     */
    @PostMapping("/submit")
    private String submitApplication(@RequestBody Student student) {
        Map<String, Object> map = studentService.submitApplication(student);
        if (Integer.parseInt(String.valueOf(map.get("code"))) == 1) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<GrantedAuthority> authList = new ArrayList<>();
            authList.add(new SimpleGrantedAuthority("STUDENT_IS_SUBMIT"));
            Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authList);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        return JSONArray.toJSONString(map);
    }

    /**
     * 更改学生的审核状态
     *
     * @param stuNo  学号
     * @param status 审核状态
     * @return 更改成功/失败结果
     */
    @PostMapping("/verify")
    private String modifyStatus(@RequestParam("stuNo") String stuNo,
                                @RequestParam("status") String status) {

        return JSONArray.toJSONString(studentService.modifyStatus(status, stuNo));
    }

    /**
     * 学生上传证明材料的图片
     *
     * @param file 图片文件
     * @return 上传成功/失败结果
     */
    @PostMapping("/upload")
    private String uploadImage(MultipartFile file, Principal principal) throws Exception {

        String stuNo = principal.getName();

        Map<String, Object> map = studentService.uploadImg(file, stuNo);

        return JSONArray.toJSONString(map);
    }

    /**
     * 通过学号获取证明材料图片的URL
     *
     * @param stuNo 学号
     * @return 证明材料URL
     */
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
