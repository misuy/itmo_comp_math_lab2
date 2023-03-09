package org.example.calculatingTree.entities.values;

import java.util.Map;

public class Variable extends Value {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getValue(Map<String, Double> variables) {
        return variables.get(this.name);
    }
}
