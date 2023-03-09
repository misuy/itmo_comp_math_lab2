package org.example.calculatingTree.entities.operations;

import org.example.calculatingTree.entities.Node;

public abstract class UnaryOperation extends Operation {
    private Node child;
    public void setChild(Node child) {
        this.child = child;
    }
}
