package org.example.methods;

import org.example.calculatingTree.entities.Node;
import org.example.calculatingTree.entities.operations.BinaryOperation;
import org.example.calculatingTree.entities.operations.Mul;
import org.example.calculatingTree.entities.operations.Sum;
import org.example.calculatingTree.entities.values.Constant;
import org.example.calculatingTree.entities.values.Variable;
import org.example.entities.Function;
import org.example.entities.Segment;

public class SimpleIterationMethodForSystemOfTwoEquations {
    public static double findLambda(Function function, Segment[] segments) {
        return SimpleIterationMethodForSystemOfTwoEquations.findLambda(function, segments, 0.001);
    }

    public static double findLambda(Function function, Segment[] segments, double stepSize) {
        double[] xVector = new double[2];
        xVector[0] = segments[0].getLeftBorder();

        double maxDerivative = -Double.MAX_VALUE;

        while (xVector[0] <= segments[0].getRightBorder()) {
            xVector[1] = segments[1].getLeftBorder();
            while (xVector[1] <= segments[1].getRightBorder()) {
                double derivative = function.getFirstPartialDerivativeValue(xVector, 0) + function.getFirstPartialDerivativeValue(xVector, 1);
                if (derivative > maxDerivative) maxDerivative = derivative;
                xVector[1] += stepSize;
            }
            xVector[0] += stepSize;
        }

        System.out.println(-1 / maxDerivative);
        return - 1 / maxDerivative;
    }

    public static Function buildPhi(Function function, Segment[] segments, int index) {
        Constant lambda = new Constant();
        lambda.setValue(SimpleIterationMethodForSystemOfTwoEquations.findLambda(function, segments));

        BinaryOperation mul = new Mul();
        mul.setLeftChild(lambda);
        mul.setRightChild(function.getCalculatingTree());

        Variable variable = new Variable();
        variable.setName("x_" + index);

        BinaryOperation sum = new Sum();
        sum.setLeftChild(variable);
        sum.setRightChild(mul);

        return new Function(sum);
    }

    public static double getMaxVectorsDelta(double[] vector1, double[] vector2) {
        double maxDelta = -Double.MAX_VALUE;
        for (int i=0; i<vector1.length; i++) {
            double delta = Math.abs(vector1[i] - vector2[i]);
            if (delta > maxDelta) maxDelta = delta;
        }
        return maxDelta;
    }

    public static double[] getRoot(Function[] functions, Segment[] segments, double accuracy) {
        Function[] phis = new Function[functions.length];
        for (int i=0; i<functions.length; i++) phis[i] = SimpleIterationMethodForSystemOfTwoEquations.buildPhi(functions[i], segments, i);

        double[] xVector = new double[segments.length];
        double[] xPrevVector = new double[segments.length];
        for (int i=0; i<segments.length; i++) {
            xVector[i] = segments[i].getLeftBorder();
            xPrevVector[i] = segments[i].getLeftBorder() - 2 * accuracy;
        }

        while (SimpleIterationMethodForSystemOfTwoEquations.getMaxVectorsDelta(xVector, xPrevVector) > accuracy) {
            xPrevVector = xVector.clone();
            System.out.println("---");
            for (int i=0; i<xVector.length; i++) {
                System.out.println(xPrevVector[i]);
                xVector[i] = phis[i].getValueByVariables(xPrevVector);
            }
        }

        return xVector;
    }
}
