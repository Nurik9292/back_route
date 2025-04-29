package com.takykulgam.ugur_v2.applications.boundaries.output;

import java.util.List;

public record OutputRoute(long id, String name, int number, String dir, List<Point> path, City city) {

    public record Point(double lat, double lng) {}

    public record City(long id, String name) {}
}

