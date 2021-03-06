package com.scnu.peexamsystem.service.student;

import com.scnu.peexamsystem.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface StudentService {
    Map<String, Object> queryStudentList(String stuName, String instituteNo, String classNo, String grade, String status, int currentPageNo, int pageSize);

    Map<String, Object> queryStudent(String stuNo);

    Map<String, Object> studentLogin(String stuNo, String password);

    Map<String, Object> registerStudent(String stuNo, String password);

    Map<String, Object> studentLogout(boolean isRemoveSession, String stuNo);

    Map<String, Object> submitApplication(Student student, boolean isSession);

    Map<String, Object> modifyStatus(String status, String stuNo);

    Map<String, Object> uploadImg(MultipartFile file,String stuNo) throws Exception;

}
