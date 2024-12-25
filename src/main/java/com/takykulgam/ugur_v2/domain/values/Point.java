package com.takykulgam.ugur_v2.domain.values;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;

public record Point(double x, double y) {

    public void validation() {
        if (x < 35.00 || x > 42.00)
            throw new CoreException("Широта должна быть в диапазоне от 35.00 до 42.00");

        if (y < 52.00 || y > 67.00)
            throw new CoreException("Долгота должна быть в диапазоне от 52.00 до 67.00");
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(x, y);
    }
}