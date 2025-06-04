package com.dev.dto.response;

import com.dev.dto.BaseDTO;
import com.dev.enums.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse extends BaseDTO {

    private Authority authority;
}
