package com.ecommerce.analysis.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecommerce.analysis.entity.User;
import com.ecommerce.analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        // 先检查用户是否存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User existingUser = userService.getOne(wrapper);
        
        if (existingUser == null) {
            return Map.of("success", false, "message", "用户不存在，请先注册");
        }
        
        // 检查密码是否正确
        User user = userService.login(username, password);
        if (user != null) {
            return Map.of("success", true, "user", user);
        } else {
            return Map.of("success", false, "message", "密码错误，请检查输入");
        }
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String role = params.get("role");

        boolean success = userService.register(username, password, role);
        if (success) {
            return Map.of("success", true, "message", "注册成功");
        } else {
            return Map.of("success", false, "message", "用户名已存在");
        }
    }

    @GetMapping("/list")
    public Map<String, Object> getUserList() {
        List<User> users = userService.list();
        return Map.of("success", true, "data", users);
    }

    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String role = params.get("role");

        boolean success = userService.register(username, password, role);
        if (success) {
            return Map.of("success", true, "message", "添加用户成功");
        } else {
            return Map.of("success", false, "message", "用户名已存在");
        }
    }

    @PostMapping("/update")
    public Map<String, Object> updateUser(@RequestBody Map<String, String> params) {
        Integer id = Integer.parseInt(params.get("id"));
        String username = params.get("username");
        String password = params.get("password");
        String role = params.get("role");

        User user = userService.getById(id);
        if (user == null) {
            return Map.of("success", false, "message", "用户不存在");
        }

        if (username != null) {
            user.setUsername(username);
        }
        if (password != null) {
            user.setPassword(password);
        }
        if (role != null) {
            user.setRole(role);
        }

        boolean success = userService.updateById(user);
        if (success) {
            return Map.of("success", true, "message", "更新用户成功");
        } else {
            return Map.of("success", false, "message", "更新用户失败");
        }
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteUser(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");

        boolean success = userService.removeById(id);
        if (success) {
            return Map.of("success", true, "message", "删除用户成功");
        } else {
            return Map.of("success", false, "message", "删除用户失败");
        }
    }
}
