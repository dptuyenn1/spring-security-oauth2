package com.dev.demo.dto.response;

import com.dev.demo.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse extends BaseDTO {

    private String firstName;
    private String lastName;
    private String username;
    private Set<String> roles;
}
