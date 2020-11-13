package com.scnu.peexamsystem.service.admin;

import com.scnu.peexamsystem.dao.admin.AdminDao;
import com.scnu.peexamsystem.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {
    @Autowired
    AdminDao adminDao;


    @Override
    public UserDetails loadUserByUsername(String adminID) throws UsernameNotFoundException {
        Admin admin = adminDao.findAdminByAdminID(adminID);
        if (admin == null)
            throw new UsernameNotFoundException("管理员不存在");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        User user = new User(admin.getAdminID(), admin.getPassword(), authorities);
        return user;

    }

    /**
     * 管理员登录
     * @param adminID 管理员id
     * @param password 密码
     * @return msg 返回消息
     *         code 状态码，1成功，0失败
     *         result boolean是否登录成功
     */
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

    /**
     * 管理员退出登录
     * @param adminID 管理员id
     * @return msg 返回消息
     *         code 状态码，1成功，0失败
     *         result boolean是否退出登录成功
     */
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
