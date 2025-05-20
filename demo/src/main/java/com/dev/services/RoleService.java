package com.dev.services;

import com.dev.enums.Authority;
import com.dev.models.Role;

public interface RoleService {

    Role create(Role role);

    Role findByAuthority(Authority authority);
}
