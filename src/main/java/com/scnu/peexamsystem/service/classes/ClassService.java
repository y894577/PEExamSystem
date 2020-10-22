package com.scnu.peexamsystem.service.classes;

import java.util.Map;

public interface ClassService {
    Map<String, Object> getGradeList();

    Map<String, Object> queryClassList(String instituteNo, String grade);
}
