package com.dev.models;

import com.dev.enums.Authority;
import com.dev.helpers.Constants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constants.TABLE_NAMES.ROLE)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Role extends BaseModel {
    
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
