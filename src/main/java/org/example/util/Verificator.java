package org.example.util;

import org.example.entities.Function;
import org.example.entities.Segment;

import java.util.ArrayList;
import java.util.List;

public class Verificator {
    public static int rootsCountOnSegment(Function function, Segment segment, double accuracy) {
        double x = segment.getLeftBorder();
        int counter = 0;
        while (x <= segment.getRightBorder()) {
            if (function.getValueByVariable(x) * function.getValueByVariable(x + accuracy) < 0) counter++;
            x += accuracy;
        }
        return counter;
    }

    public static List<Segment> getRootsSegments(Function function, Segment segment, double accuracy) {
        List<Segment> segments = new ArrayList<>();
        double x = segment.getLeftBorder();

        while (x <= segment.getRightBorder()) {
            if (function.getValueByVariable(x) * function.getValueByVariable(x + accuracy) < 0) segments.add(new Segment(x, x + accuracy));
            x += accuracy;
        }

        return segments;
    }
}
