package com.dev.repositories;

import com.dev.models.RefreshToken;
import com.dev.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByValue(UUID value);
}
