package org.example.calculatingTree.entities.operations;

import java.util.Map;

public class Dif extends BinaryOperation {
    @Override
    public double getValue(Map<String, Double> variables) {
        return this.leftChild.getValue(variables) - this.rightChild.getValue(variables);
    }
}
