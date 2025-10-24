package com.dev.services.impl;

import com.dev.dto.requests.RoleRequest;
import com.dev.dto.responses.RoleResponse;
import com.dev.enums.Authority;
import com.dev.exceptions.ConflictException;
import com.dev.exceptions.NotFoundException;
import com.dev.helpers.Constants;
import com.dev.mappers.RoleMapper;
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
    private final RoleMapper roleMapper;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleRepository.findByAuthority(request.getAuthority()).orElse(null);

        if (role != null)
            throw new ConflictException(
                    MessageFormat.format(Constants.EXCEPTION_MESSAGES.DUPLICATED,
                            "Role with authority: " + request.getAuthority()));

        role = roleMapper.toRole(request);

        return roleMapper.toRoleResponse(roleRepository.save(role));
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
