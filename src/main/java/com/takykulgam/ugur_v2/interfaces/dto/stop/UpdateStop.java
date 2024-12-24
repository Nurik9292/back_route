package com.takykulgam.ugur_v2.interfaces.dto.stop;

import com.takykulgam.ugur_v2.applications.iteractor.stop.UpdateStopUseCase;

public record UpdateStop(String name, double lat, double lng, long cityId) {
    public UpdateStopUseCase.Input toInput(long id) {
        return new UpdateStopUseCase.Input(
                id,
                name,
                new UpdateStopUseCase.Point(lat, lng),
                new UpdateStopUseCase.City(cityId));
    }
}
