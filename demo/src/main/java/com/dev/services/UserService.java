package com.dev.services;

import com.dev.dto.request.RegisterRequest;
import com.dev.models.User;

public interface UserService {

    User create(User user);

    User create(RegisterRequest request);

    User findByUsername(String username);

    User getByUsername(String username);
}
