package com.dev.models;

import com.dev.enums.Type;
import com.dev.helpers.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = Constants.TABLE_NAMES.INVALID_TOKEN)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class InvalidToken extends BaseModel {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private Date expiredAt;
    private Date revokedAt;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    private User user;
}
