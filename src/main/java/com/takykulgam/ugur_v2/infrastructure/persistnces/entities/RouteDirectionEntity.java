package com.takykulgam.ugur_v2.infrastructure.persistnces.entities;

import lombok.Data;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("route_directions")
public class RouteDirectionEntity {

    @Id
    private Long id;
    private String direction;
    @Column("route_id")
    private Long routeId;
    @Column("path")
    private LineString path;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
