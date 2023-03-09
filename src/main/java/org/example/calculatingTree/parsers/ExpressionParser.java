package org.example.calculatingTree.parsers;

import org.example.calculatingTree.entities.operations.BinaryOperation;
import org.example.calculatingTree.entities.operations.UnaryOperation;
import org.example.calculatingTree.entities.values.Constant;
import org.example.calculatingTree.entities.Node;
import org.example.calculatingTree.entities.values.Variable;

import java.util.Map;
import java.util.Stack;

public class ExpressionParser {
    private static Map<String, OperationDescription> operations = OperationDescription.getAllOperationDescriptions();

    private static void addOperationOnStack(Stack<Node> stack, OperationDescription operationDescription) throws IllegalAccessException, InstantiationException {
        if (operationDescription.getChildCount() == 1) {
            Node child = stack.pop();
            UnaryOperation operation = (UnaryOperation) operationDescription.getOperation();
            operation.setChild(child);
            stack.push(operation);
        }
        else if (operationDescription.getChildCount() == 2) {
            Node rightChild = stack.pop();
            Node leftChild = stack.pop();
            BinaryOperation operation = (BinaryOperation) operationDescription.getOperation();
            operation.setLeftChild(leftChild);
            operation.setRightChild(rightChild);
            stack.push(operation);
        }
    }

    public static Node parseExpression(String expression) throws IllegalAccessException, InstantiationException {
        String[] tokens = expression.split(" ");

        Stack<Node> mainStack = new Stack<>();
        Stack<OperationDescription> operationsStack = new Stack<>();

        for (String token: tokens) {
            OperationDescription currentOperationDescription = ExpressionParser.operations.get(token);
            if (currentOperationDescription != null) {
                if (currentOperationDescription == OperationDescription.closingBracket) {
                    while (!operationsStack.empty() && (operationsStack.peek() != OperationDescription.openingBracket)) {
                        ExpressionParser.addOperationOnStack(mainStack, operationsStack.pop());
                    }
                    operationsStack.pop();
                }
                else if (currentOperationDescription == OperationDescription.openingBracket) operationsStack.push(currentOperationDescription);
                else {
                    while (!operationsStack.empty() && (operationsStack.peek().getPriority() >= currentOperationDescription.getPriority())) {
                        ExpressionParser.addOperationOnStack(mainStack, operationsStack.pop());
                    }
                    operationsStack.push(currentOperationDescription);
                }
            }
            else {
                try {
                    double value = Double.parseDouble(token);
                    Constant constant = new Constant();
                    constant.setValue(value);
                    mainStack.push(constant);
                }
                catch (NumberFormatException ex) {
                    Variable variable = new Variable();
                    variable.setName(token);
                    mainStack.push(variable);
                }
            }
        }
        while (!operationsStack.empty()) ExpressionParser.addOperationOnStack(mainStack, operationsStack.pop());
        return mainStack.pop();
    }
}
