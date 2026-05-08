package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.User;
import com.ecommerce.analysis.mapper.UserMapper;
import com.ecommerce.analysis.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String username, String password) {
        // 先根据用户名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        
        // 如果用户不存在，返回null
        if (user == null) {
            return null;
        }
        
        // 如果用户存在，检查密码是否正确
        if (!user.getPassword().equals(password)) {
            return null;
        }
        
        return user;
    }

    @Override
    public boolean register(String username, String password, String role) {
        // 检查用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (baseMapper.selectOne(wrapper) != null) {
            return false;
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role != null ? role : "user"); // 设置角色，默认为普通用户
        return baseMapper.insert(user) > 0;
    }
}
