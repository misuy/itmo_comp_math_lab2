package org.example.util;

import org.example.entities.Function;
import org.example.entities.Segment;

import java.util.ArrayList;
import java.util.List;

public class Verificator {
    public static List<Segment> getRootsSegments(Function function, Segment segment, double stepSize) {
        List<Segment> segments = new ArrayList<>();
        double x = segment.getLeftBorder();

        while (x <= segment.getRightBorder()) {
            if (function.getValueByVariable(x) * function.getValueByVariable(x + stepSize) < 0) segments.add(new Segment(x, x + stepSize));
            x += stepSize;
        }

        return segments;
    }
}
