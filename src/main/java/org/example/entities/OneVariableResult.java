package org.example.entities;

public class OneVariableResult {
    private Double root;
    private int iterationsCount;

    public OneVariableResult(double root, int iterationsCount) {
        this.root = root;
        this.iterationsCount = iterationsCount;
    }

    public Double getRoot() {
        return this.root;
    }

    public int getIterationsCount() {
        return this.iterationsCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("=");
        builder.append(this.root);
        builder.append("(число итераций: ");
        builder.append(this.iterationsCount);
        builder.append(")");
        return builder.toString();
    }
}
