package com.dev.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO implements Serializable {

    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
}
