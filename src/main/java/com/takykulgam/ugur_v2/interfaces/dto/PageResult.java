package com.takykulgam.ugur_v2.interfaces.dto;

import java.util.List;


public record PageResult<T>(List<T> items, boolean isLastPage) {
}
