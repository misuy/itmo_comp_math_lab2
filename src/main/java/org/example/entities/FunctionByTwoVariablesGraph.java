package org.example.entities;

import java.util.LinkedList;
import java.util.List;

public class FunctionByTwoVariablesGraph extends FunctionGraph {
    private double stepSize;
    private double accuracy;
    public FunctionByTwoVariablesGraph(Function function, Segment[] segments, double stepSize, double accuracy) {
        this.function = function;
        this.leftBorder = segments[0].getLeftBorder();
        this.rightBorder = segments[0].getRightBorder();
        this.bottomBorder = segments[1].getLeftBorder();
        this.topBorder = segments[1].getRightBorder();
        this.stepSize = stepSize;
        this.accuracy = accuracy;
    }

    public FunctionByTwoVariablesGraph(Function function, Segment[] segments, double accuracy) {
        this.function = function;
        this.leftBorder = segments[0].getLeftBorder();
        this.rightBorder = segments[0].getRightBorder();
        this.bottomBorder = segments[1].getLeftBorder();
        this.topBorder = segments[1].getRightBorder();
        this.stepSize = 0.001;
        this.accuracy = accuracy;
    }

    @Override
    public List<Double> getValuesByVariable(double x0) {
        List<Double> valuesList = new LinkedList<>();
        double[] xArr = new double[2];
        xArr[0] = x0;
        xArr[1] = this.bottomBorder;
        while (xArr[1] <= this.topBorder) {
            if (Math.abs(this.function.getValueByVariables(xArr)) < this.accuracy) valuesList.add(xArr[1]);
            xArr[1] += this.stepSize;
        }
        return valuesList;
    }
}
