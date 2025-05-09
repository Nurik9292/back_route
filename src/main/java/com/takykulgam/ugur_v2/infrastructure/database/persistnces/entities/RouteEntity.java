package com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("routes")
public class RouteEntity {

    @Id
    private Long id;
    private String name;
    private int number;
    private int interval;
    @Column("city_id")
    private long cityId;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
