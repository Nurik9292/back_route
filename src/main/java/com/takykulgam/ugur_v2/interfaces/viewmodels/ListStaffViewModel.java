package com.takykulgam.ugur_v2.interfaces.viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListStaffViewModel {
    @JsonProperty("data")
    private List<Staff> data;

}
