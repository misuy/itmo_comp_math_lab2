package org.example.entities;

public class GraphDot {
    private double horizontalCoordinate;
    private double verticalCoordinate;

    public GraphDot(double horizontalCoordinate, double verticalCoordinate) {
        this.horizontalCoordinate = horizontalCoordinate;
        this.verticalCoordinate = verticalCoordinate;
    }

    public double getHorizontalCoordinate() {
        return this.horizontalCoordinate;
    }

    public double getVerticalCoordinate() {
        return this.verticalCoordinate;
    }
}
