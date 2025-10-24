package com.dev.services;

import com.dev.dto.requests.RoleRequest;
import com.dev.dto.responses.RoleResponse;
import com.dev.enums.Authority;
import com.dev.models.Role;

public interface RoleService {

    Role create(Role role);

    RoleResponse create(RoleRequest request);

    Role findByAuthority(Authority authority);
}
