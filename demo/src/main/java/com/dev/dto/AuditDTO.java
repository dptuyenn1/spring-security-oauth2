package com.dev.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public abstract class AuditDTO implements Serializable {

    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
}
