package org.example.methods;

import org.example.entities.Function;
import org.example.entities.Segment;

public class ChordMethod {
    public static double findRoot(Function function, Segment segment, double accuracy) {
        Segment curSegment = segment;
        while (curSegment.getLength() > accuracy) {
            double a = curSegment.getLeftBorder();
            double f_a = function.getValueByVariable(a);
            double b = curSegment.getRightBorder();
            double f_b = function.getValueByVariable(b);

            double x = a - ((b - a) / (f_b - f_a)) * f_a;

            if (function.getValueByVariable(a) * function.getValueByVariable(x) < 0) curSegment = new Segment(a, x);
            else curSegment = new Segment(x, b);
        }
        return (curSegment.getLeftBorder() + curSegment.getRightBorder()) / 2;
    }
}
