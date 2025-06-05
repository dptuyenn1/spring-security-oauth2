package com.dev.models;

import com.dev.helpers.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = Constants.TABLE_NAMES.REFRESH_TOKEN)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID value;
    private Date expiredAt;
    @OneToOne
    @MapsId
    private User user;
}
