package com.takykulgam.ugur_v2.infrastructure.persistnces.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "banners")
public class BannerEntity {

    @Id
    private long id;

    @Column("image_url")
    private String imageUrl;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public BannerEntity(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
