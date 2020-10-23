package com.scnu.peexamsystem.dao.admin;

import com.scnu.peexamsystem.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDao {

    Admin findAdminByAdminIDAndPassword(@Param("adminID") String adminID,
                                        @Param("password") String password);
}
