package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.User;

public interface UserService extends IService<User> {

    User login(String username, String password);

    boolean register(String username, String password, String role);
}
