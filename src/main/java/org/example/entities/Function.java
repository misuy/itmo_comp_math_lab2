package org.example.entities;

import org.example.calculatingTree.entities.Node;

import java.util.Map;
import java.util.TreeMap;

public class Function {
    private Node calculatingTree;

    public Function(Node calculatingTree) {
        this.calculatingTree = calculatingTree;
    }

    public Node getCalculatingTree() {
        return this.calculatingTree;
    }

    public double getValueByVariable(double x) {
        Map<String, Double> variables = new TreeMap<>();
        variables.put("x", x);
        return this.calculatingTree.getValue(variables);
    }

    public double getValueByVariables(double[] xArray) {
        Map<String, Double> variables = new TreeMap<>();
        for (int i=0; i<xArray.length; i++) variables.put("x_" + i, xArray[i]);
        return this.calculatingTree.getValue(variables);
    }

    public double getFirstDerivativeValue(double x) {
        return this.getFirstDerivativeValue(x, 0.001);
    }

    public double getFirstDerivativeValue(double x, double accuracy) {
        return (this.getValueByVariable(x + accuracy) - this.getValueByVariable(x - accuracy)) / (2 * accuracy);
    }

    public double getFirstPartialDerivativeValue(double[] xArray, int index) {
        return this.getFirstPartialDerivativeValue(xArray, index, 0.001);
    }

    public double getFirstPartialDerivativeValue(double[] xArray, int index, double accuracy) {
        double[] xArray1 = xArray.clone();
        xArray1[index] += accuracy;
        double[] xArray2 = xArray.clone();
        xArray2[index] -= accuracy;

        return (this.getValueByVariables(xArray1) - this.getValueByVariables(xArray2)) / (2 * accuracy);
    }

    public double getSecondDerivativeValue(double x) {
        return this.getSecondDerivativeValue(x, 0.001);
    }

    public double getSecondDerivativeValue(double x, double accuracy) {
        return (this.getValueByVariable(x + accuracy) - 2 * this.getValueByVariable(x) + this.getValueByVariable(x - accuracy)) / Math.pow(accuracy, 2);
    }
}
