package org.example;

import org.example.calculatingTree.parsers.ExpressionParser;
import org.example.entities.Function;
import org.example.entities.Segment;
import org.example.methods.ChordMethod;
import org.example.methods.NewtonMethod;
import org.example.methods.SimpleIterationMethod;
import org.example.methods.SimpleIterationMethodForSystemOfTwoEquations;

public class Main {
    public static void main(String[] args) {
        try {
            Function[] functions = new Function[2];
            functions[0] = new Function(ExpressionParser.parseExpression("x_0 ^ 3 - x_0 + 4 - x_1"));
            functions[1] = new Function(ExpressionParser.parseExpression("x_0 ^ 2 + 4 - x_1"));
            Segment[] segments = new Segment[2];
            segments[0] = new Segment(-1, -0.5);
            segments[1] = new Segment(4, 4.5);
            System.out.println(SimpleIterationMethodForSystemOfTwoEquations.getRoot(functions, segments, 0.001)[1]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}