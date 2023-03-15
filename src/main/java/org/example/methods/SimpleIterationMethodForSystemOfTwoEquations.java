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
    public static double findLambda(Function function, Segment[] segments, double accuracy) {
        return SimpleIterationMethodForSystemOfTwoEquations.findLambda(function, segments, 0.001, accuracy);
    }

    public static double findLambda(Function function, Segment[] segments, double stepSize, double accuracy) throws IllegalArgumentException {
        double[] xVector = new double[2];
        xVector[0] = segments[0].getLeftBorder();
        xVector[1] = segments[1].getLeftBorder();
        Double lambdaSign = null;
        double maxDerivative = 0;

        while (xVector[0] <= segments[0].getRightBorder()) {
            xVector[1] = segments[1].getLeftBorder();
            while (xVector[1] <= segments[1].getRightBorder()) {
                if (Math.abs(function.getValueByVariables(xVector)) < accuracy) {
                    double derivative = function.getFirstPartialDerivativeValue(xVector, 0) + function.getFirstPartialDerivativeValue(xVector, 1);
                    if (lambdaSign == null) lambdaSign = derivative / Math.abs(derivative);
                    if (lambdaSign * derivative < 0) throw new IllegalArgumentException();
                    if (Math.abs(derivative) > maxDerivative) maxDerivative = Math.abs(derivative);
                }
                xVector[1] += stepSize;
            }
            xVector[0] += stepSize;
        }
        if (maxDerivative < accuracy) throw new IllegalArgumentException();
        return - lambdaSign / maxDerivative;
    }

    public static Function buildPhi(Function function, Segment[] segments, int index, double accuracy) throws IllegalArgumentException {
        Constant lambda = new Constant();
        lambda.setValue(SimpleIterationMethodForSystemOfTwoEquations.findLambda(function, segments, accuracy));

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

    public static double[] getRoot(Function[] functions, Segment[] segments, double accuracy) throws IllegalArgumentException {
        Function[] phis = new Function[functions.length];
        for (int i=0; i<functions.length; i++) phis[i] = SimpleIterationMethodForSystemOfTwoEquations.buildPhi(functions[i], segments, i, accuracy);

        double[] xVector = new double[segments.length];
        double[] xPrevVector = new double[segments.length];
        for (int i=0; i<segments.length; i++) {
            xVector[i] = segments[i].getLeftBorder();
            xPrevVector[i] = segments[i].getLeftBorder() - 2 * accuracy;
        }

        while (SimpleIterationMethodForSystemOfTwoEquations.getMaxVectorsDelta(xVector, xPrevVector) > accuracy) {
            xPrevVector = xVector.clone();
            for (int i=0; i<xVector.length; i++) {
                xVector[i] = phis[i].getValueByVariables(xPrevVector);
            }
        }

        return xVector;
    }
}
