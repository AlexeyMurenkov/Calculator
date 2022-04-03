package by.yandex.practicum.calculator;

import java.util.Stack;

public class Calculator {
    static final char LEFT_BRACE = '(';
    static final char RIGHT_BRACE = ')';

    final Stack<Operation> operations = new Stack<>();
    final Stack<Double> numbers = new Stack<>();
    private final String expression;
    private int iterator;

    private Calculator(String expression, int iterator) {
        this.iterator = iterator;
        this.expression = expression;
    }

    private int getIterator() {
        return iterator;
    }

    private boolean nextIterate() {
        do {
            iterator++;
        } while (iterator < expression.length() && expression.charAt(iterator) == ' ');
        return iterator < expression.length();
    }
    private Double parseDouble() {
        Double parsed = null;
        int iterator = this.iterator;
        while (iterator <= expression.length()) {
            try {
                parsed = Double.parseDouble(this.expression.substring(this.iterator, ++iterator));
            } catch (Exception e) {
                this.iterator = iterator - 1;
                return parsed;
            }
        }
        return null;
    }

    private Double calcTop(int order) {
        while (!operations.isEmpty() && operations.peek().order >= order) {
            if (numbers.size() > 1) { // Для упрощения положим, что все операции бинарные и инфиксные
                final Operation o = operations.pop();
                double x1 = numbers.pop();
                double x2 = numbers.pop();
                numbers.push(o.calc(x1, x2));
            } else {
                return null;
            }
        }
        if (numbers.isEmpty()) {
            return null;
        }
        return numbers.pop();
    }

    private Double calculate() {
        while (nextIterate() && expression.charAt(iterator) != RIGHT_BRACE) {
            if (expression.charAt(iterator) == LEFT_BRACE) {
                final Calculator calculator = new Calculator(expression, iterator);
                Double result = calculator.calculate();
                if (result == null) {
                    return null;
                }
                numbers.push(result);
                iterator = calculator.getIterator();
            } else {
                final Double number = parseDouble();
                if (number != null) {
                    iterator--;
                    numbers.push(number);
                } else {
                    final Operation operation = Operation.operation(expression.charAt(iterator));
                    if (numbers.isEmpty() && operations.isEmpty() && operation == Operation.SUB) { // Это унарный минус
                        operations.push(Operation.SUB);
                        numbers.push(0.0);
                    } else if (operation != null) {
                        Double result = calcTop(operation.order);
                        if (result != null) {
                            numbers.push(result);
                        } else {
                            return null;
                        }
                        operations.push(operation);
                    }
                }
            }
        }
        final Double result = calcTop(Integer.MIN_VALUE);
        if (numbers.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }

    public static Double calculate(String expression) {
        if (expression != null) {
            Calculator calculator = new Calculator(expression, -1);
            return calculator.calculate();
        }
        return null;
    }
}