package com.dev.demo.services;

import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.models.User;

public interface UserService {

    User create(User user);

    User create(RegisterRequest request);

    User findByUsername(String username);
}
