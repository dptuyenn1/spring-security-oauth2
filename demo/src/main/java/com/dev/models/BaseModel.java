package com.dev.models;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseModel {

    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        isActive = true;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
