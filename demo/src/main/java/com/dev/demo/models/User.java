package com.dev.demo.models;

import com.dev.demo.helpers.Constants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = Constants.TABLE_NAMES.USER)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            joinColumns = {@JoinColumn(name = Constants.USERS_ROLES.JOIN_COLUMN)},
            inverseJoinColumns = {@JoinColumn(name = Constants.USERS_ROLES.INVERSE_JOIN_COLUMN)}
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return super.getIsActive();
    }
}
