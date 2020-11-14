package com.scnu.peexamsystem.service.student;

import com.scnu.peexamsystem.entity.Student;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface StudentService {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    Map<String, Object> queryStudentList(String stuName, String instituteNo, String classNo, String grade, String status, int currentPageNo, int pageSize);

    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT_IS_SUBMIT')")
    Map<String, Object> queryStudent(String stuNo);

    Map<String, Object> studentLogin(String stuNo, String password);

    Map<String, Object> registerStudent(String stuNo, String password);

    Map<String, Object> studentLogout(String stuNo);

    @PreAuthorize("hasAnyAuthority('STUDENT_NOT_SUBMIT')")
    Map<String, Object> submitApplication(Student student);

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    Map<String, Object> modifyStatus(String status, String stuNo);

    @PreAuthorize("hasAnyAuthority('STUDENT_NOT_SUBMIT')")
    Map<String, Object> uploadImg(MultipartFile file,String stuNo) throws Exception;

}
