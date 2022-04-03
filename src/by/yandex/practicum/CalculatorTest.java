package by.yandex.practicum;

import by.yandex.practicum.calculator.Calculator;

import java.util.Scanner;

public class CalculatorTest {
    public static void main(final String[] args) {
        final Scanner scanner= new Scanner(System.in);
        System.out.println("Тест строчного калькулятора");
        while (true) {
            System.out.println("Введите выражение или оставьте строку пустой для выхода");
            final String expression = scanner.nextLine();
            if (expression.length() == 0) {
                break;
            }
            Double result = Calculator.calculate(expression);
            if (result == null) {
                System.out.println("Такое выражение я посчитать не могу (((");
            } else {
                System.out.println(expression + " = " + result);
            }
        }
    }

}
