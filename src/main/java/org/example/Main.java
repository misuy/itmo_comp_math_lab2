package org.example;

import org.example.calculatingTree.parsers.ExpressionParser;
import org.example.entities.*;
import org.example.methods.ChordMethod;
import org.example.methods.NewtonMethod;
import org.example.methods.SimpleIterationMethod;
import org.example.methods.SimpleIterationMethodForSystemOfTwoEquations;
import org.example.util.GraphPanel;
import org.example.util.Verificator;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.example.util.Verificator.getRootsSegments;

public class Main {
    public static void main(String[] args) {
        try {
            JFrame jFrame = new JFrame();
            GraphPanel panel = new GraphPanel();

            Scanner scanner = null;
            if (args.length != 0) {
                FileInputStream fileInputStream = new FileInputStream(args[0]);
                scanner = new Scanner(fileInputStream);
            }
            else scanner = new Scanner(System.in);
            System.out.println("Привет! Добро пожаловать в лабораторную работу №2 по вычислительной математике. Надеюсь, используя эту программу, вы почувствуете страдания, испытанные мной во время ее создания :)");
            System.out.println("Что будем решать? (\n1 -- нелинейное уравнение;\n2 -- систему из двух нелинейных уравнений\n): ");
            int answer = scanner.nextInt();
            switch (answer) {
                case (1):
                    Main.equationSolving(scanner, panel);
                    break;
                case (2):
                    Main.systemOfEquationsSolving(scanner, panel);
                    break;
                default:
                    throw new IllegalArgumentException("Выбрана несуществующая опция :(");
            }

            jFrame.add(panel);
            jFrame.setSize(800, 800);
            jFrame.setVisible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void equationSolving(Scanner scanner, GraphPanel panel) throws IllegalAccessException, InstantiationException {
        System.out.println("Замечательно! Введите, пожалуйста, функцию f(x), для которой будет решено уравнение f(x)=0 (Очень прошу, вводите все символы через пробел. Мне очень не хотелось делать нормальный парсер. Например, вместо x^3+x, нужно ввести x ^ 3 + x): ");
        scanner.nextLine();
        String expression = scanner.nextLine();
        Function function = new Function(ExpressionParser.parseExpression(expression));
        System.out.println("Теперь введите левую границу интервала, на котором будем искать корень: ");
        double leftBorder = scanner.nextDouble();
        System.out.println("Правую: ");
        double rightBorder = scanner.nextDouble();
        Segment usersSegment = new Segment(leftBorder, rightBorder);
        List<Segment> segments = Verificator.getRootsSegments(function, usersSegment, 0.1);
        if (segments.size() == 0) throw new IllegalArgumentException("На интервале не найдено корней :(");
        else System.out.println("Количество корней на интервале: " + segments.size());
        System.out.println("Какая точность?: ");
        double accuracy = scanner.nextDouble();
        System.out.println("Какой метод предпочитаете? (\n1 -- метод хорд;\n2 -- метод Ньютона;\n3 -- метод простой итерации\n): ");
        int answer = scanner.nextInt();

        List<OneVariableResult> roots = new LinkedList<>();
        switch (answer) {
            case (1):
                for (Segment segment: segments) roots.add(ChordMethod.findRoot(function, segment, accuracy));
                break;
            case (2):
                for (Segment segment: segments) roots.add(NewtonMethod.findRoot(function, segment, accuracy));
                break;
            case (3):
                for (Segment segment: segments) roots.add(SimpleIterationMethod.findRoot(function, segment, accuracy));
                break;
            default:
                throw new IllegalArgumentException("Выбрана несуществующая опция :(");
        }
        for (int i=0; i<roots.size(); i++) {
            System.out.println("Корень_" + (i+1) + " " + roots.get(i).toString() + ";");
        }
        FunctionByOneVariableGraph graph = new FunctionByOneVariableGraph(function, usersSegment);
        panel.addFunctionGraph(graph);
    }


    public static void systemOfEquationsSolving(Scanner scanner, GraphPanel panel) throws IllegalAccessException, InstantiationException {
        System.out.println("Прекрасно! Введите, пожалуйста, функции f_0(x_0, x_1) и f_1(x_0, x_1), для которой будет решена система f_0(x_0, x_1)=0 and f_1(x_0, x_1)=0 (Очень прошу, вводите все символы через пробел. Мне очень не хотелось делать нормальный парсер. Например, вместо x^3+x, нужно ввести x ^ 3 + x).");
        System.out.println("Введите f_0(x_0, x_1): ");
        scanner.nextLine();
        String expression0 = scanner.nextLine();

        System.out.println("Введите f_1(x_0, x_1): ");
        String expression1 = scanner.nextLine();

        Function[] functions = new Function[2];
        functions[0] = new Function(ExpressionParser.parseExpression(expression0));
        functions[1] = new Function(ExpressionParser.parseExpression(expression1));

        System.out.println("Теперь введите левую границу интервала для x_0: ");
        double x0LeftBorder = scanner.nextDouble();
        System.out.println(x0LeftBorder);
        System.out.println("Правую: ");
        double x0RightBorder = scanner.nextDouble();
        System.out.println("Теперь левую границу для x_1: ");
        double x1LeftBorder = scanner.nextDouble();
        System.out.println("Правую: ");
        double x1RightBorder = scanner.nextDouble();

        Segment[] segments = new Segment[2];
        segments[0] = new Segment(x0LeftBorder, x0RightBorder);
        segments[1] = new Segment(x1LeftBorder, x1RightBorder);

        System.out.println("Какая точность?: ");
        double accuracy = scanner.nextDouble();

        TwoVariablesResult root = SimpleIterationMethodForSystemOfTwoEquations.getRoot(functions, segments, accuracy);

        System.out.println("Корень " + root + ";");

        FunctionByTwoVariablesGraph graph0 = new FunctionByTwoVariablesGraph(functions[0], segments, 0.01);
        FunctionByTwoVariablesGraph graph1 = new FunctionByTwoVariablesGraph(functions[1], segments, 0.01);
        panel.addFunctionGraph(graph0);
        panel.addFunctionGraph(graph1);
    }
}