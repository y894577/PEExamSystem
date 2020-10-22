package com.scnu.peexamsystem.dao.institute;

import com.scnu.peexamsystem.entity.Institute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InstituteDao {
    List<Institute> findAll();
}
