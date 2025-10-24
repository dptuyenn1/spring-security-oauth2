package com.dev.mappers;

import com.dev.dto.requests.RoleRequest;
import com.dev.dto.responses.RoleResponse;
import com.dev.models.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toRoleResponse(Role role);

    Role toRole(RoleRequest request);
}
