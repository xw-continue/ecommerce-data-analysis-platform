package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.Admin;

public interface AdminService extends IService<Admin> {

    Admin login(String username, String password);

    boolean updatePassword(Long id, String oldPassword, String newPassword);
    
    boolean register(String username, String password);
}