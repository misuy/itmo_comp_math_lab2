package org.example.calculatingTree.entities.operations;

import java.util.Map;

public class Pow extends BinaryOperation {
    @Override
    public double getValue(Map<String, Double> variables) {
        return Math.pow(this.leftChild.getValue(variables), this.rightChild.getValue(variables));
    }
}
