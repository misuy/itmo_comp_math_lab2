package org.example.methods;

import org.example.entities.Function;
import org.example.entities.OneVariableResult;
import org.example.entities.Segment;

public class ChordMethod {
    public static OneVariableResult findRoot(Function function, Segment segment, double accuracy) {
        Segment curSegment = segment;
        int iterationsCount = 0;
        while (curSegment.getLength() > accuracy) {
            iterationsCount++;
            double a = curSegment.getLeftBorder();
            double f_a = function.getValueByVariable(a);
            double b = curSegment.getRightBorder();
            double f_b = function.getValueByVariable(b);

            double x = a - ((b - a) / (f_b - f_a)) * f_a;

            if (function.getValueByVariable(a) * function.getValueByVariable(x) == 0) return new OneVariableResult(x, iterationsCount);
            else if (function.getValueByVariable(a) * function.getValueByVariable(x) < 0) curSegment = new Segment(a, x);
            else curSegment = new Segment(x, b);
        }
        return new OneVariableResult((curSegment.getLeftBorder() + curSegment.getRightBorder()) / 2, iterationsCount);
    }
}
