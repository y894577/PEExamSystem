package com.scnu.peexamsystem.dao.admin;

import com.scnu.peexamsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

    boolean existsAdminByAdminIDAndPassword(String adminID, String password);
}
