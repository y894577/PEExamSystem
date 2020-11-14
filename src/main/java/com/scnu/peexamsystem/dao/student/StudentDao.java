package com.scnu.peexamsystem.dao.student;

import com.scnu.peexamsystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentDao {

    Student findByStuNoAndPassword(@Param("stuNo") String stuNo,
                                 @Param("password") String password);

    List<Student> findAllByCondition(@Param("stuName") String stuName,
                                     @Param("instituteNo") String instituteNo,
                                     @Param("classNo") String classNo,
                                     @Param("grade") String grade,
                                     @Param("status") String status);

    int insertStudent(@Param("stuNo") String stuNo,
                      @Param("password") String password);

    int existStudent(@Param("stuNo") String stuNo);

    int updateStudent(Student student);

    Student findStudentDetailByStuNo(@Param("stuNo") String stuNo);

    Student findStudentByStuNo(@Param("stuNo") String stuNo);

    int updateStudentVerifyStatus(@Param("status") String status,
                                  @Param("stuNo") String stuNo);

}
