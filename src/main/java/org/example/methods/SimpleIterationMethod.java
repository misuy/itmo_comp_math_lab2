package org.example.methods;

import org.example.calculatingTree.entities.operations.BinaryOperation;
import org.example.calculatingTree.entities.operations.Mul;
import org.example.calculatingTree.entities.operations.Sum;
import org.example.calculatingTree.entities.values.Constant;
import org.example.calculatingTree.entities.values.Variable;
import org.example.entities.Function;
import org.example.entities.Segment;

public class SimpleIterationMethod {
    private static double findLambda(Function function, Segment segment) {
        return SimpleIterationMethod.findLambda(function, segment, 0.001);
    }

    private static double findLambda(Function function, Segment segment, double stepSize) {
        double x = segment.getLeftBorder();
        double maxDerivative = -Double.MAX_VALUE;
        while (x <= segment.getRightBorder()) {
            double derivative = function.getFirstDerivativeValue(x);
            if (derivative > maxDerivative) maxDerivative = derivative;
            x += stepSize;
        }
        return - 1 / maxDerivative;
    }

    private static Function buildPhi(Function function, Segment segment) {
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

    public static double findRoot(Function function, Segment segment, double accuracy) {
        Function phi = SimpleIterationMethod.buildPhi(function, segment);

        double x = segment.getLeftBorder();
        double prevX = x - 2 * accuracy;

        while (Math.abs(x - prevX) > accuracy) {
            prevX = x;
            x = phi.getValueByVariable(prevX);
        }

        return x;
    }
}
