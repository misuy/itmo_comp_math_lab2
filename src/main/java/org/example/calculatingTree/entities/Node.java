package org.example.calculatingTree.entities;

import java.util.Map;

public abstract class Node {
    public abstract double getValue(Map<String, Double> variables);
}
