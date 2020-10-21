package com.scnu.peexamsystem.service.student;

import com.scnu.peexamsystem.dao.student.StudentDao;
import com.scnu.peexamsystem.entity.Institute;
import com.scnu.peexamsystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Override
    public Map<String, Object> queryStudentList(String stuName, String instituteNo, String classNo, String grade, String status) {

        if (!StringUtils.isEmpty(stuName))
            stuName = "%" + stuName + "%";

        List<Student> list = studentDao.findAllByCondition(stuName, instituteNo, classNo, grade, status);

        Map<String, Object> map = new HashMap<>();
        map.put("result", list.toArray());

        return map;
    }

    @Override
    public Map<String, Object> queryStudent(String stuNo) {
        Map<String, Object> map = new HashMap<>();

        Student student = studentDao.findStudentByStuNo(stuNo);
        boolean isQuery = student != null;
        map.put("msg", "查询" + (isQuery ? "成功" : "失败"));
        map.put("result", student);
        map.put("code", isQuery ? 1 : 0);

        return map;
    }

    @Override
    public Map<String, Object> studentLogin(String stuNo, String password) {
        Map<String, Object> map = new HashMap<>();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        boolean isLogin = studentDao.existsByStuNoAndPassword(stuNo, password) > 0;
        map.put("msg", "登录" + (isLogin ? "成功" : "失败"));
        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        map.put("result", result);
        map.put("code", isLogin ? 1 : 0);
        return map;
    }

    @Override
    public Map<String, Object> registerStudent(String stuNo, String password) {
        Map<String, Object> map = new HashMap<>();
        boolean isExist = studentDao.existStudent(stuNo) > 0;

        if (isExist) {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            boolean isRegister = studentDao.insertStudent(stuNo, password) > 0;
            map.put("msg", "注册" + (isRegister ? "成功" : "失败"));
            map.put("code", isRegister ? 1 : 0);
        } else {
            map.put("msg", "该用户已被注册");
            map.put("code", -1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        map.put("result", result);

        return map;
    }

    @Override
    public Map<String, Object> studentLogout(String stuNo) {
        Map<String, Object> map = new HashMap<>();

        map.put("msg", "退出登录成功");
        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        map.put("result", result);
        map.put("code", 1);

        return map;
    }

    @Override
    public Map<String, Object> submitApplication(Student student) {
        Map<String, Object> map = new HashMap<>();

        boolean isUpdate = studentDao.updateStudent(student) > 0;

        map.put("msg", "提交申请" + (isUpdate ? "成功" : "失败"));
        map.put("result", student);
        map.put("code", isUpdate ? 1 : 0);

        return map;
    }


}
