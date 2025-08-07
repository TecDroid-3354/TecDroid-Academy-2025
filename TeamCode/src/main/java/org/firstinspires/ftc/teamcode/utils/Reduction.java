package org.firstinspires.ftc.teamcode.utils;

public class Reduction {
    private final double ratio;

    public Reduction(double ratio) {
        this.ratio = ratio;
    }

    public double apply(double value) {
        return value / ratio;
    }

    public double unApply(double value) {
        return value * ratio;
    }
}
