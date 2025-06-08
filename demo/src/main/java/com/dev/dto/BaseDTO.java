package com.dev.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDTO extends AuditDTO {

    private Boolean isActive;
}
