package com.takykulgam.ugur_v2.interfaces.dto.stop;

import com.takykulgam.ugur_v2.applications.iteractor.stop.CreateStopUseCase;

public record CreateStop(String name, double lat, double lng, long cityId) {
    public CreateStopUseCase.Input toInput() {
        return new CreateStopUseCase.Input(name, new CreateStopUseCase.Point(lat, lng), new CreateStopUseCase.City(cityId));
    }
}
