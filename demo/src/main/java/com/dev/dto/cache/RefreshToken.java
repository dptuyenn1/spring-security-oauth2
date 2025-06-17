package com.dev.dto.cache;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class RefreshToken {

    private UUID id;
    private Date revokedAt;
}
