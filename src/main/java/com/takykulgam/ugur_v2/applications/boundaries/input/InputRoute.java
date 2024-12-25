package com.takykulgam.ugur_v2.applications.boundaries.input;

import java.util.List;

public record InputRoute(String name, int number, String dir, List<Point> path, long cityId) {

    public record Point(int lat, int lng) {}
}
