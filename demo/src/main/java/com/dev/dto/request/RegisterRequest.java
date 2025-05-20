package com.dev.dto.request;

import com.dev.annotations.StringsInCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @StringsInCollection(values = {"ADMIN", "USER"})
    private Set<String> roles = new HashSet<>();
}
