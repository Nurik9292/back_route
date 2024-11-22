package com.takykulgam.ugur_v2.infrastructure.persistnces.entities;

import com.takykulgam.ugur_v2.infrastructure.security.sessions.Session;
import com.takykulgam.ugur_v2.infrastructure.security.sessions.SessionUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class StaffEntity implements SessionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isAdmin;
    private String password;
    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private StaffSessionEntity staffSession;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public StaffEntity(String name, String password, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public StaffSessionEntity getSession() {
        return getStaffSession();
    }
}
