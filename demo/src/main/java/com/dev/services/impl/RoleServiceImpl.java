package com.dev.services.impl;

import com.dev.enums.Authority;
import com.dev.exceptions.NotFoundException;
import com.dev.helpers.Constants;
import com.dev.models.Role;
import com.dev.repositories.RoleRepository;
import com.dev.services.RoleService;
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
    public Role findByAuthority(Authority authority) {
        return roleRepository
                .findByAuthority(authority)
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(Constants.EXCEPTION_MESSAGES.NOT_FOUND,
                                "Role with name: " + authority.name())));
    }
}
