package com.takykulgam.ugur_v2.interfaces.dto.route;

import java.util.List;

public record CreateRoute(String name, int number, List<Point> toPoints, List<Point> fromPoints) {

    public record Point(double lat, double lng) {}
}
