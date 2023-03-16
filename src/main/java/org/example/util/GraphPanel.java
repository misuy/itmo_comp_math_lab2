package org.example.util;

import org.example.entities.FunctionGraph;
import org.example.entities.GraphDot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;


public class GraphPanel extends JPanel implements MouseListener {
    {
        this.addMouseListener(this);
    }
    private double leftBorder = Double.MAX_VALUE;
    private double rightBorder = -Double.MAX_VALUE;
    private double bottomBorder = Double.MAX_VALUE;
    private double topBorder = -Double.MAX_VALUE;
    private double widthPerPixel = 1;
    private double heightPerPixel = 1;
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

        this.calculateHorizontalBorders();

        this.widthPerPixel = (this.rightBorder - this.leftBorder) / this.getWidth();

        List<List<GraphDot>> dotsList = new LinkedList<>();
        for (FunctionGraph functionGraph: this.functionGraphs) dotsList.add(this.getFunctionGraphDots(functionGraph));

        this.heightPerPixel = (this.topBorder - this.bottomBorder) / this.getHeight();

        this.drawAxis(graphics2D);
        for (List<GraphDot> dots: dotsList) this.plotGraphDots(graphics2D, dots);
    }

    private void calculateHorizontalBorders() {
        for (FunctionGraph functionGraph: this.functionGraphs) {
            if (functionGraph.getLeftBorder() < this.leftBorder) this.leftBorder = functionGraph.getLeftBorder();
            if (functionGraph.getRightBorder() > this.rightBorder) this.rightBorder = functionGraph.getRightBorder();
        }
    }

    private List<GraphDot> getFunctionGraphDots(FunctionGraph functionGraph) {
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

    private void plotGraphDots(Graphics2D graphics, List<GraphDot> dots) {
        for (GraphDot dot: dots) {
            graphics.drawLine(this.translateHorizontalCoordinateToPixels(dot.getHorizontalCoordinate()), this.translateVerticalCoordinateToPixels(dot.getVerticalCoordinate()),
                    this.translateHorizontalCoordinateToPixels(dot.getHorizontalCoordinate()), this.translateVerticalCoordinateToPixels(dot.getVerticalCoordinate()));
        }
    }

    private void drawAxis(Graphics2D graphics) {
        graphics.drawLine(0, this.translateVerticalCoordinateToPixels(0), this.getWidth(), this.translateVerticalCoordinateToPixels(0));
        graphics.drawLine(this.translateHorizontalCoordinateToPixels(0), 0, this.translateHorizontalCoordinateToPixels(0), this.getHeight());
    }

    private int translateHorizontalCoordinateToPixels(double coordinate) {
        return (int) ((coordinate - this.leftBorder) / widthPerPixel);
    }

    private int translateVerticalCoordinateToPixels(double coordinate) {
        return (int) (this.getHeight() - 1 - ((coordinate - this.bottomBorder) / heightPerPixel));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("horizontal: " + (this.leftBorder + e.getX() * this.widthPerPixel) + ", vertical: " + (this.bottomBorder + (this.getHeight() - e.getY()) * this.heightPerPixel));
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
