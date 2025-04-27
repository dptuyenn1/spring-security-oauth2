package com.dev.demo.mappers.impl;

import com.dev.demo.dto.response.UserResponse;
import com.dev.demo.mappers.Mapper;
import com.dev.demo.models.Role;
import com.dev.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResponse> {

    private final ModelMapper modelMapper;

    @Override
    public User mapFromDTOToModel(UserResponse userResponse) {
        return modelMapper.map(userResponse, User.class);
    }

    @Override
    public UserResponse mapFromModelToDTO(User user) {
        TypeMap<User, UserResponse> typeMap = modelMapper.createTypeMap(User.class, UserResponse.class);

        Converter<Set<Role>, Set<String>> converter = new AbstractConverter<>() {
            @Override
            protected Set<String> convert(Set<Role> roles) {
                return roles
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());
            }
        };

        typeMap.addMappings(mapper -> mapper
                .using(converter)
                .map(User::getRoles, UserResponse::setRoles));

        return typeMap.map(user);
    }
}
