package org.example.calculatingTree.entities.operations;

import org.example.calculatingTree.entities.operations.BinaryOperation;

import java.util.Map;

public class Sum extends BinaryOperation {
    @Override
    public double getValue(Map<String, Double> variables) {
        return this.leftChild.getValue(variables) + this.rightChild.getValue(variables);
    }
}
