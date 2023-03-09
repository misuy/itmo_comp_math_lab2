package org.example.calculatingTree.entities.operations;

import org.example.calculatingTree.entities.Node;

public abstract class BinaryOperation extends Operation {
    protected Node leftChild;
    protected Node rightChild;

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
