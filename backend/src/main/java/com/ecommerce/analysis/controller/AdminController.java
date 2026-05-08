package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.Admin;
import com.ecommerce.analysis.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            return Map.of("success", true, "admin", admin);
        } else {
            return Map.of("success", false, "message", "用户名或密码错误");
        }
    }

    @PostMapping("/update-password")
    public Map<String, Object> updatePassword(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String oldPassword = params.get("oldPassword").toString();
        String newPassword = params.get("newPassword").toString();
        
        boolean success = adminService.updatePassword(id, oldPassword, newPassword);
        if (success) {
            return Map.of("success", true, "message", "密码修改成功");
        } else {
            return Map.of("success", false, "message", "旧密码错误");
        }
    }
    
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        boolean success = adminService.register(username, password);
        if (success) {
            return Map.of("success", true, "message", "注册成功");
        } else {
            return Map.of("success", false, "message", "用户名已存在");
        }
    }
}