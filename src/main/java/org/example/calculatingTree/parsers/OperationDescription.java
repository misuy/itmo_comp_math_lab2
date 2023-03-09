package org.example.calculatingTree.parsers;

import org.example.calculatingTree.entities.operations.*;
import org.example.calculatingTree.entities.Node;

import java.util.Map;
import java.util.TreeMap;

public class OperationDescription {
    private final Class<? extends Operation> operationClass;
    private final int priority;
    private final int childCount;
    public static OperationDescription openingBracket = new OperationDescription(null, -1, -1);
    public static OperationDescription closingBracket = new OperationDescription(null, -1, -1);

    private OperationDescription(Class<? extends Operation> operationClass, int priority, int childCount) {
        this.operationClass = operationClass;
        this.priority = priority;
        this.childCount = childCount;
    }

    public Node getOperation() throws IllegalAccessException, InstantiationException {
        return this.operationClass.newInstance();
    }

    public int getPriority() {
        return this.priority;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public static Map<String, OperationDescription> getAllOperationDescriptions() {
        Map<String,OperationDescription> operationDescriptions = new TreeMap<>();

        operationDescriptions.put("(", OperationDescription.openingBracket);
        operationDescriptions.put(")", OperationDescription.closingBracket);
        operationDescriptions.put("+", new OperationDescription(Sum.class, 0, 2));
        operationDescriptions.put("-", new OperationDescription(Dif.class, 0, 2));
        operationDescriptions.put("*", new OperationDescription(Mul.class, 1, 2));
        operationDescriptions.put("/", new OperationDescription(Div.class, 1, 2));
        operationDescriptions.put("^", new OperationDescription(Pow.class, 2, 2));

        return operationDescriptions;
    }
}
