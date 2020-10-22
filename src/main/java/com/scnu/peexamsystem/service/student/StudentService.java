package com.scnu.peexamsystem.service.student;

import com.scnu.peexamsystem.entity.Student;

import java.util.Map;

public interface StudentService {
    Map<String, Object> queryStudentList(String stuName, String instituteNo, String classNo, String grade, String status, int currentPageNo, int pageSize);

    Map<String, Object> queryStudent(String stuNo);

    Map<String, Object> studentLogin(String stuNo, String password);

    Map<String, Object> registerStudent(String stuNo, String password);

    Map<String, Object> studentLogout(String stuNo);

    Map<String, Object> submitApplication(Student student);

    Map<String, Object> modifyStatus(int status,String stuNo);

}
