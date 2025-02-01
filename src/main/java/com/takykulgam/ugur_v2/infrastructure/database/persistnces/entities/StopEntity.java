package com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("stops")
public class StopEntity {

    @Id
    private Long id;
    private String name;
    @Column("order_index")
    private int order;
    @Column("is_intermediate")
    private boolean isIntermediate;
    @Column("route_direction_id")
    private Long RouteDirectionId;
    @Column("city_id")
    private Long cityId;
    @Column("location")
    private Point location;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public StopEntity(String name, Long cityId) {
        this.name = name;
        this.cityId = cityId;
        this.order = 0;
        this.isIntermediate = false;
    }

    public StopEntity(String name, Long cityId, Point location) {
        this.name = name;
        this.cityId = cityId;
        this.location = location;
        this.order = 0;
        this.isIntermediate = false;
    }
}
