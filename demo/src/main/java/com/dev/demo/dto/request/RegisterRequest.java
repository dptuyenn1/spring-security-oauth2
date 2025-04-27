package com.dev.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();
}
