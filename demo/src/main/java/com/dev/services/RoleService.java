package com.dev.services;

import com.dev.models.Role;

public interface RoleService {

    Role create(Role role);

    Role findByName(String name);
}
