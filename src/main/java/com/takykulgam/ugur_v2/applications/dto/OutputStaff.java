package com.takykulgam.ugur_v2.applications.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputStaff {

    private Long id;
    private String name;
    @JsonIgnore
    private String password;
    private boolean isAdmin;


}
