package com.dev.demo.models;

import com.dev.demo.helpers.Constants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = Constants.TABLE_NAMES.USER)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            joinColumns = {@JoinColumn(name = Constants.USERS_ROLES.JOIN_COLUMN)},
            inverseJoinColumns = {@JoinColumn(name = Constants.USERS_ROLES.INVERSE_JOIN_COLUMN)}
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
