package com.takykulgam.ugur_v2.infrastructure.persistnces.entities;

import com.takykulgam.ugur_v2.infrastructure.security.sessions.Session;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "staff_sessions")
public class StaffSessionEntity implements Session {

    @Id
    private long id;
    private String token;
    @Column("staff_id")
    private long staffId;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
