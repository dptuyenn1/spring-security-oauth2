package com.dev.demo.services.impl;

import com.dev.demo.models.Role;
import com.dev.demo.repositories.RoleRepository;
import com.dev.demo.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }
}
