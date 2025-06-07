package com.dev.repositories;

import com.dev.models.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, UUID> {

    Optional<InvalidToken> findByToken(String token);
}
