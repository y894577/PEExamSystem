package com.scnu.peexamsystem.service.admin;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

public interface AdminService {
    Map<String, Object> adminLogin(String adminID, String password);

    Map<String, Object> adminLogout(String adminID);

}
