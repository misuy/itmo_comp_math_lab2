package org.example;

import org.example.calculatingTree.parsers.ExpressionParser;
import org.example.entities.*;
import org.example.methods.ChordMethod;
import org.example.methods.NewtonMethod;
import org.example.methods.SimpleIterationMethod;
import org.example.util.GraphPanel;
import org.example.util.Verificator;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.example.util.Verificator.getRootsSegments;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Привет! Добро пожаловать в лабораторную работу №2 по вычислительной математике. Надеюсь, используя эту программу, вы почувствуете страдания, испытанные мной во время ее создания :)");
            System.out.print("Что будем решать? (\n1 -- нелинейное уравнение;\n2 -- систему из двух нелинейных уравнений\n): ");
            int answer = scanner.nextInt();
            switch (answer) {
                case (1):
                    Main.equationSolving(scanner);
                    break;
                case (2):

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void equationSolving(Scanner scanner) throws IllegalAccessException, InstantiationException {
        System.out.println("Замечательно! Введите, пожалуйста, функцию f(x), для которой будет решено уравнение f(x)=0 (Очень прошу, вводите все символы через пробел. Мне очень не хотелось делать нормальный парсер. Например, вместо x^3+x, нужно ввести x ^ 3 + x): ");
        scanner.nextLine();
        String expression = scanner.nextLine();
        Function function = new Function(ExpressionParser.parseExpression(expression));
        System.out.print("Прекрасно! Теперь введите левую границу интервала, на котором будем искать корень: ");
        double leftBorder = scanner.nextDouble();
        System.out.print("Теперь правую: ");
        double rightBorder = scanner.nextDouble();
        Segment usersSegment = new Segment(leftBorder, rightBorder);
        List<Segment> segments = Verificator.getRootsSegments(function, usersSegment, 0.1);
        if (segments.size() == 0) {
            System.out.println("На интервале не найдено корней :(");
            throw new IllegalArgumentException();
        }
        else System.out.println("Количество корней на интервале: " + segments.size());
        System.out.print("Какая точность?: ");
        double accuracy = scanner.nextDouble();
        System.out.print("Какой метод предпочитаете? (\n1 -- метод хорд;\n2 -- метод Ньютона;\n3 -- метод простой итерации\n): ");
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
    }


    public static void systemOfEquationsSolving(Scanner scanner) {

    }
}