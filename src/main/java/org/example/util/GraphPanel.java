package org.example.util;

import org.example.entities.FunctionGraph;
import org.example.entities.GraphDot;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class GraphPanel extends JPanel {
    private double leftBorder = Double.MAX_VALUE;
    private double rightBorder = -Double.MAX_VALUE;
    private double bottomBorder = Double.MAX_VALUE;
    private double topBorder = -Double.MAX_VALUE;
    private List<FunctionGraph> functionGraphs = new LinkedList<>();

    public void addFunctionGraph(FunctionGraph functionGraph) {
        this.functionGraphs.add(functionGraph);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics2D);

        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.setFont(new Font("serif", Font.PLAIN, 20));
        graphics2D.drawLine(0, 0, 100, 100);

        this.calculateHorizontalBorders();

        double widthPerPixel = (this.rightBorder - this.leftBorder) / this.getWidth();

        List<List<GraphDot>> dotsList = new LinkedList<>();
        for (FunctionGraph functionGraph: this.functionGraphs) dotsList.add(this.getFunctionGraphDots(functionGraph, widthPerPixel));

        double heightPerPixel = (this.topBorder - this.bottomBorder) / this.getHeight();

        this.drawAxis(graphics2D, widthPerPixel, heightPerPixel);
        for (List<GraphDot> dots: dotsList) this.plotGraphDots(graphics2D, dots, widthPerPixel, heightPerPixel);
    }

    private void calculateHorizontalBorders() {
        for (FunctionGraph functionGraph: this.functionGraphs) {
            if (functionGraph.getLeftBorder() < this.leftBorder) this.leftBorder = functionGraph.getLeftBorder();
            if (functionGraph.getRightBorder() > this.rightBorder) this.rightBorder = functionGraph.getRightBorder();
        }
    }

    private List<GraphDot> getFunctionGraphDots(FunctionGraph functionGraph, double widthPerPixel) {
        List<GraphDot> dots = new LinkedList<>();
        int pixelNumber = 0;
        while (pixelNumber < this.getWidth()) {
            for (double value: functionGraph.getValuesByVariable(this.leftBorder + pixelNumber*widthPerPixel)) {
                dots.add(new GraphDot(this.leftBorder + pixelNumber*widthPerPixel, value));
            }
            pixelNumber++;
        }
        if (functionGraph.getBottomBorder() < this.bottomBorder) this.bottomBorder = functionGraph.getBottomBorder();
        if (functionGraph.getTopBorder() > this.topBorder) this.topBorder = functionGraph.getTopBorder();
        return dots;
    }

    public void drawAxis(Graphics2D graphics, double widthPerPixel, double heightPerPixel) {
        int verticalCoordinateOfHorizontalAxis;
        if (this.bottomBorder >= 0) verticalCoordinateOfHorizontalAxis = this.getHeight();
        else if (this.topBorder <= 0) verticalCoordinateOfHorizontalAxis = 0;
        else verticalCoordinateOfHorizontalAxis = this.translateVerticalCoordinateToPixels(0, heightPerPixel);
        graphics.drawLine(0, verticalCoordinateOfHorizontalAxis, this.getWidth(), verticalCoordinateOfHorizontalAxis);
        for (int i=(int) Math.ceil(this.leftBorder); i<=this.rightBorder; i++) {
            graphics.drawLine(this.translateHorizontalCoordinateToPixels(i, widthPerPixel), verticalCoordinateOfHorizontalAxis - 5, this.translateHorizontalCoordinateToPixels(i, widthPerPixel), verticalCoordinateOfHorizontalAxis + 5);
            graphics.drawString(Integer.toString(i), this.translateHorizontalCoordinateToPixels(i, widthPerPixel) + 5, verticalCoordinateOfHorizontalAxis + 25);
        }

        int horizontalCoordinateOfVerticalAxis;
        if (this.leftBorder >= 0) horizontalCoordinateOfVerticalAxis = 0;
        else if (this.rightBorder <= 0) horizontalCoordinateOfVerticalAxis = this.getWidth();
        else horizontalCoordinateOfVerticalAxis = this.translateHorizontalCoordinateToPixels(0, widthPerPixel);
        graphics.drawLine(horizontalCoordinateOfVerticalAxis, 0, horizontalCoordinateOfVerticalAxis, this.getHeight());
        for (int i=(int) Math.ceil(this.bottomBorder); i<=this.topBorder; i++) {
            graphics.drawLine(horizontalCoordinateOfVerticalAxis - 5, this.translateVerticalCoordinateToPixels(i, heightPerPixel), horizontalCoordinateOfVerticalAxis + 5, this.translateVerticalCoordinateToPixels(i, heightPerPixel));
            if (i != 0) graphics.drawString(Integer.toString(i), horizontalCoordinateOfVerticalAxis + 10, this.translateVerticalCoordinateToPixels(i, heightPerPixel) + 5);
        }
    }

    private void plotGraphDots(Graphics2D graphics, List<GraphDot> dots, double widthPerPixel, double heightPerPixel) {
        for (GraphDot dot: dots) {
            graphics.drawLine(this.translateHorizontalCoordinateToPixels(dot.getHorizontalCoordinate(), widthPerPixel), this.translateVerticalCoordinateToPixels(dot.getVerticalCoordinate(), heightPerPixel),
                    this.translateHorizontalCoordinateToPixels(dot.getHorizontalCoordinate(), widthPerPixel), this.translateVerticalCoordinateToPixels(dot.getVerticalCoordinate(), heightPerPixel));
        }
    }

    private int translateHorizontalCoordinateToPixels(double coordinate, double widthPerPixel) {
        return (int) ((coordinate - this.leftBorder) / widthPerPixel);
    }

    private int translateVerticalCoordinateToPixels(double coordinate, double heightPerPixel) {
        return (int) (this.getHeight() - 1 - ((coordinate - this.bottomBorder) / heightPerPixel));
    }
}
