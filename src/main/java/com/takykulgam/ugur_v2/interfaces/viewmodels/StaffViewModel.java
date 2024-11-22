package com.takykulgam.ugur_v2.interfaces.viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StaffViewModel {
    @JsonProperty("data")
    private Staff data;
}
