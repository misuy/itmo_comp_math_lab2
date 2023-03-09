package org.example.entities;

public class Segment {
    private double leftBorder;
    private double rightBorder;

    public Segment(double leftBorder, double rightBorder) {
        if (leftBorder > rightBorder) throw new IllegalArgumentException();
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    public double getLeftBorder() {
        return this.leftBorder;
    }

    public double getRightBorder() {
        return this.rightBorder;
    }

    public double getLength() {
        return this.rightBorder - this.leftBorder;
    }
}
