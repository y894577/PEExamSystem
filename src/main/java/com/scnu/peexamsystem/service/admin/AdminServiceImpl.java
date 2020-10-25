package com.scnu.peexamsystem.service.admin;

import com.scnu.peexamsystem.dao.admin.AdminDao;
import com.scnu.peexamsystem.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;


import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;


    @Override
    public Map<String, Object> adminLogin(String adminID, String password) {
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        Admin admin = adminDao.findAdminByAdminIDAndPassword(adminID, pwd);
        boolean isLogin = admin != null;
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        if (isLogin) {
            result.put("adminID", admin.getAdminID());
            result.put("adminName", admin.getAdminName());
        }
        map.put("msg", "登录" + (isLogin ? "成功" : "失败"));
        map.put("result", result);
        map.put("code", isLogin ? 1 : 0);

        return map;
    }

    @Override
    public Map<String, Object> adminLogout(String adminID) {
        Map<String, Object> map = new HashMap<>();

        map.put("msg", "退出登录成功");
        Map<String, Object> result = new HashMap<>();
        result.put("adminID", adminID);
        map.put("result", result);
        map.put("code", 1);

        return map;
    }


}
