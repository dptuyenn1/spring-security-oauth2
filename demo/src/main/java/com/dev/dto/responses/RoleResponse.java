package com.dev.dto.responses;

import com.dev.dto.BaseDTO;
import com.dev.enums.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleResponse extends BaseDTO {

    private UUID id;
    private Authority authority;
}
