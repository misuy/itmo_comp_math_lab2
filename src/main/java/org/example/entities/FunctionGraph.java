package org.example.entities;

import java.util.List;

public abstract class FunctionGraph {
    protected Function function = null;
    protected double leftBorder = Double.MAX_VALUE;
    protected double rightBorder = -Double.MAX_VALUE;
    protected double bottomBorder = Double.MAX_VALUE;
    protected double topBorder = -Double.MAX_VALUE;

    public double getLeftBorder() {
        return this.leftBorder;
    }

    public double getRightBorder() {
        return this.rightBorder;
    }

    public double getBottomBorder() {
        return this.bottomBorder;
    }

    public double getTopBorder() {
        return this.topBorder;
    }

    public abstract List<Double> getValuesByVariable(double x);
}
