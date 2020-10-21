package com.scnu.peexamsystem.service.admin;

import com.scnu.peexamsystem.dao.admin.AdminDao;
import com.scnu.peexamsystem.dao.student.StudentDao;
import com.scnu.peexamsystem.entity.Admin;
import com.scnu.peexamsystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;


    @Override
    public Map<String, Object> adminLogin(String adminID, String password) {
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        boolean isLogin = adminDao.existsAdminByAdminIDAndPassword(adminID, pwd);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", adminID);
        map.put("msg", "登录" + (isLogin ? "成功" : "失败"));
        map.put("result", result);
        map.put("code", isLogin ? 1 : 0);
        return map;
    }

    @Override
    public boolean adminLogout(String adminID) {
        return false;
    }



}
