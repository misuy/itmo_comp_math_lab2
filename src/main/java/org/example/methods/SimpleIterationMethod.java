package org.example.methods;

import org.example.calculatingTree.entities.operations.BinaryOperation;
import org.example.calculatingTree.entities.operations.Mul;
import org.example.calculatingTree.entities.operations.Sum;
import org.example.calculatingTree.entities.values.Constant;
import org.example.calculatingTree.entities.values.Variable;
import org.example.entities.Function;
import org.example.entities.OneVariableResult;
import org.example.entities.Segment;

public class SimpleIterationMethod {
    private static double findLambda(Function function, Segment segment) {
        return SimpleIterationMethod.findLambda(function, segment, 0.001);
    }

    private static double findLambda(Function function, Segment segment, double stepSize) throws IllegalArgumentException {
        double x = segment.getLeftBorder();
        double lambdaSign = function.getFirstDerivativeValue(x) / Math.abs(function.getFirstDerivativeValue(x));
        double maxDerivative = 0;
        while (x <= segment.getRightBorder()) {
            double derivative = function.getFirstDerivativeValue(x);
            if (derivative * lambdaSign < 0) throw new IllegalArgumentException("Функция не удовлетворяет достаточным условиям сходимости метода простой итерации на этом интервале :(");
            if (Math.abs(derivative) > maxDerivative) maxDerivative = Math.abs(derivative);
            x += stepSize;
        }
        return - lambdaSign / maxDerivative;
    }

    private static Function buildPhi(Function function, Segment segment) throws IllegalArgumentException {
        Constant lambda = new Constant();
        lambda.setValue(SimpleIterationMethod.findLambda(function, segment));

        BinaryOperation mul = new Mul();
        mul.setLeftChild(lambda);
        mul.setRightChild(function.getCalculatingTree());

        Variable variable = new Variable();
        variable.setName("x");

        BinaryOperation sum = new Sum();
        sum.setLeftChild(variable);
        sum.setRightChild(mul);

        return new Function(sum);
    }

    public static OneVariableResult findRoot(Function function, Segment segment, double accuracy) throws IllegalArgumentException {
        Function phi = SimpleIterationMethod.buildPhi(function, segment);

        double x = segment.getLeftBorder();
        double prevX = x - 2 * accuracy;

        int iterationsCount = 0;
        while (Math.abs(x - prevX) > accuracy) {
            iterationsCount++;
            prevX = x;
            x = phi.getValueByVariable(prevX);
        }

        return new OneVariableResult(x, iterationsCount);
    }
}
