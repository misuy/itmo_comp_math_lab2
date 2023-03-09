package org.example.calculatingTree.entities.values;

import org.example.calculatingTree.entities.values.Value;

import java.util.Map;

public class Constant extends Value {
    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue(Map<String, Double> variables) {
        return value;
    }
}
