package com.takykulgam.ugur_v2.interfaces.dto.city;

import com.takykulgam.ugur_v2.applications.iteractor.city.CityUpdateUseCase;

public record UpdateCity(String title) {
    public CityUpdateUseCase.Input toInput(long id) {
        return new CityUpdateUseCase.Input(id, title);
    }
}
