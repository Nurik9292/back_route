package com.takykulgam.ugur_v2.infrastructure.persistnces.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takykulgam.ugur_v2.infrastructure.security.sessions.SessionUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "staff")
public class StaffEntity implements SessionUser {

    @Id
    private Long id;
    private String name;
    @Column("is_admin")
    private boolean isAdmin;
    @JsonIgnore
    private String password;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public StaffEntity(String name, String password, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = password;
    }

}
