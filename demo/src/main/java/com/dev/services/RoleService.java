package com.dev.services;

import com.dev.dto.request.RoleRequest;
import com.dev.dto.response.RoleResponse;
import com.dev.enums.Authority;
import com.dev.models.Role;

public interface RoleService {

    Role create(Role role);

    RoleResponse create(RoleRequest request);

    Role findByAuthority(Authority authority);
}
