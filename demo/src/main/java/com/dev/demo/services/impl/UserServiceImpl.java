package com.dev.demo.services.impl;

import com.dev.demo.dto.request.RegisterRequest;
import com.dev.demo.exceptions.DuplicateException;
import com.dev.demo.helpers.Constants;
import com.dev.demo.models.Role;
import com.dev.demo.models.User;
import com.dev.demo.repositories.UserRepository;
import com.dev.demo.services.RoleService;
import com.dev.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User create(RegisterRequest request) {
        String username = request.getUsername();

        User u = userRepository.findByUsername(username).orElse(null);

        if (u != null)
            throw new DuplicateException(
                    MessageFormat.format(Constants.EXCEPTION_MESSAGES.DUPLICATED,
                            "User with username: " + username));

        Set<String> requestRoles = request.getRoles();

        Set<Role> roles = new HashSet<>();

        if (requestRoles.isEmpty())
            roles.add(roleService.findByName(ROLE_USER));
        else
            roles = requestRoles.stream().map(roleService::findByName).collect(Collectors.toSet());

        TypeMap<RegisterRequest, User> typeMap = modelMapper.createTypeMap(RegisterRequest.class, User.class);

        User user = typeMap
                .addMappings(m -> m.skip(User::setRoles))
                .map(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(Constants.EXCEPTION_MESSAGES.NOT_FOUND,
                                "User with username: " + username)));
    }
}
