package com.dev.mappers;

import com.dev.dto.request.RegisterRequest;
import com.dev.dto.response.UserResponse;
import com.dev.models.Role;
import com.dev.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserResponse userResponse);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest request);

    default Set<String> toSetString(Set<Role> roles) {
        return roles
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<Role> toSetRole(Set<String> roles) {
        return roles
                .stream()
                .map(role -> Role.builder().name(role).build())
                .collect(Collectors.toSet());
    }
}
