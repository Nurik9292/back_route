package com.takykulgam.ugur_v2.interfaces.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Staff {
    private long id;
    private String name;
    private boolean isAdmin;
}
