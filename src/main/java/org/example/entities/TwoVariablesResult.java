package org.example.entities;

public class TwoVariablesResult {
    private double[] root;
    private int iterationsCount;

    public TwoVariablesResult(double[] root, int iterationsCount) {
        this.root = root;
        this.iterationsCount = iterationsCount;
    }

    public double[] getRoot() {
        return this.root;
    }

    public int getIterationsCount() {
        return this.iterationsCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("x_0=");
        builder.append(this.root[0]);
        builder.append(", ");
        builder.append("x_1=");
        builder.append(this.root[1]);
        builder.append("(число итераций: ");
        builder.append(this.iterationsCount);
        builder.append(")");
        return builder.toString();
    }
}
