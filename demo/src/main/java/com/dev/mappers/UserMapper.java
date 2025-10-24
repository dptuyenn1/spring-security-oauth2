package com.dev.mappers;

import com.dev.dto.requests.RegisterRequest;
import com.dev.dto.responses.UserResponse;
import com.dev.models.Role;
import com.dev.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest request);

    default Set<String> toSetString(Set<Role> roles) {
        return roles
                .stream()
                .map(role -> role.getAuthority().name())
                .collect(Collectors.toSet());
    }
}
