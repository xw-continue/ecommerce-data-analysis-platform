package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.Admin;
import com.ecommerce.analysis.mapper.AdminMapper;
import com.ecommerce.analysis.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin login(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
                .eq("password", password);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
        Admin admin = baseMapper.selectById(id);
        if (admin != null && admin.getPassword().equals(oldPassword)) {
            admin.setPassword(newPassword);
            return baseMapper.updateById(admin) > 0;
        }
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        // 检查用户名是否已存在
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (baseMapper.selectOne(wrapper) != null) {
            return false;
        }
        
        // 创建新管理员
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        return baseMapper.insert(admin) > 0;
    }
}