package com.scnu.peexamsystem.service.admin;

import java.util.Map;

public interface AdminService {
    Map<String, Object> adminLogin(String adminID, String password);

    Map<String, Object> adminLogout(String adminID);

}
