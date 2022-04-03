package by.yandex.practicum.calculator;

enum Operation {
    ADD(0, '+') {
        public double calc(double x1, double x2) {
            return (x1 + x2);
        }
    },
    SUB(0, '-') {
        public double calc(double x1, double x2) {
            return (x2 - x1);
        }
    },
    DIV(1, '/') {
        public double calc(double x1, double x2) {
            return (x2 / x1);
        }
    },
    MUL(1, '*') {
        public double calc(double x1, double x2) {
            return (x1 * x2);
        }
    },
    POW(2, '^') {
        public double calc(double x1, double x2) {
            return Math.pow(x2, x1);
        }
    };

    final int order;
    private final char symbol;

    Operation (int order, char symbol) {
        this.order = order;
        this.symbol = symbol;
    }

    abstract double calc(double x1, double x2);

    static Operation operation(final char symbol) {
        for (Operation operation : values()) {
            if (symbol == operation.symbol) {
                return operation;
            }
        }
        return null;
    }
}
