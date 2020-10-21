package com.scnu.peexamsystem.service.student;

import java.util.Map;

public interface StudentService {
    Map<String, Object> queryStudent(String stuName, String instituteNo, String classNo, String grade, String status);

}
