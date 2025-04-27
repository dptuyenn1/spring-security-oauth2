package com.dev.demo.services.impl;

import com.dev.demo.exceptions.NotFoundException;
import com.dev.demo.helpers.Constants;
import com.dev.demo.models.Role;
import com.dev.demo.repositories.RoleRepository;
import com.dev.demo.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(Constants.EXCEPTION_MESSAGES.NOT_FOUND,
                                "Role with name: " + name)));
    }
}
