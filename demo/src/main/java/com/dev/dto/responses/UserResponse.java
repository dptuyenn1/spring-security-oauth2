package com.dev.dto.responses;

import com.dev.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponse extends BaseDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private Set<String> roles;
}
