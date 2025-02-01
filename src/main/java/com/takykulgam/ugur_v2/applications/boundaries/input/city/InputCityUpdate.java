package com.takykulgam.ugur_v2.applications.boundaries.input.city;

import com.takykulgam.ugur_v2.domain.entities.City;

public record InputCityUpdate(long id, String name) {
    public City toEntity() {
        return new City(id, name);
    }
}
