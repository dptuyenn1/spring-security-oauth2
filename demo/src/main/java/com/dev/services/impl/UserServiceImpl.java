package com.dev.services.impl;

import com.dev.dto.request.RegisterRequest;
import com.dev.exceptions.DuplicateException;
import com.dev.helpers.Constants;
import com.dev.mappers.UserMapper;
import com.dev.models.Role;
import com.dev.models.User;
import com.dev.repositories.UserRepository;
import com.dev.services.RoleService;
import com.dev.services.UserService;
import lombok.RequiredArgsConstructor;
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
    private final UserMapper userMapper;

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

        User user = userMapper.toUser(request);

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
