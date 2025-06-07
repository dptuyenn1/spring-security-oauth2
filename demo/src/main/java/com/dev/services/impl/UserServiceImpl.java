package com.dev.services.impl;

import com.dev.dto.request.RegisterRequest;
import com.dev.enums.Authority;
import com.dev.exceptions.DuplicateException;
import com.dev.helpers.Constants;
import com.dev.mappers.UserMapper;
import com.dev.models.User;
import com.dev.repositories.UserRepository;
import com.dev.services.RoleService;
import com.dev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null)
            throw new DuplicateException(
                    MessageFormat.format(Constants.EXCEPTION_MESSAGES.DUPLICATED,
                            "User with username: " + username));

        user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findByAuthority(Authority.USER));

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(Constants.EXCEPTION_MESSAGES.NOT_FOUND,
                                "User with username: " + username)));
    }
}
