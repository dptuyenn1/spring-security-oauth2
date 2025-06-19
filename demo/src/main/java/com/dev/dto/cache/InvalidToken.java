package com.dev.dto.cache;

import com.dev.enums.Type;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class InvalidToken {

    private UUID id;
    private Date revokedAt;
    private Type type;
}
