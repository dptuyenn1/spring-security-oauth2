package com.dev.dto.response;

import com.dev.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Set;

@Getter
@Setter
public class UserResponse extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String username;
    private Set<String> roles;
}
