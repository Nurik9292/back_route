package com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "cities")
public class CityEntity {

    @Id
    private Long id;
    @Column("title")
    private String title;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public CityEntity(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}
