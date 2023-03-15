package org.example.methods;

import org.example.entities.Function;
import org.example.entities.OneVariableResult;
import org.example.entities.Segment;

public class NewtonMethod {
    public static OneVariableResult findRoot(Function function, Segment segment, double accuracy) {
        double x;
        int iterationsCount = 0;
        if (function.getValueByVariable(segment.getLeftBorder()) * function.getSecondDerivativeValue(segment.getLeftBorder()) > 0) x = segment.getLeftBorder();
        else x = segment.getRightBorder();
        double prevX = x - 2 * accuracy;
        while (Math.abs(x - prevX) > accuracy) {
            iterationsCount++;
            prevX = x;
            x = prevX - function.getValueByVariable(prevX) / function.getFirstDerivativeValue(prevX);
        }
        return new OneVariableResult(x, iterationsCount);
    }
}
