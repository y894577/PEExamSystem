package com.scnu.peexamsystem.dao.classes;

import com.scnu.peexamsystem.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassDao {
    List<Class> findAllByInstituteNoAndGrade(@Param("instituteNo") String instituteNo,
                                             @Param("grade") String grade);
}
