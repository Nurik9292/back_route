package com.takykulgam.ugur_v2.interfaces.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    boolean success;
    T data;
}
